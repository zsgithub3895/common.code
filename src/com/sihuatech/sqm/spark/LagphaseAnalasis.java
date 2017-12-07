package com.sihuatech.sqm.spark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.hive.HiveContext;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.HasOffsetRanges;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.apache.spark.streaming.kafka.OffsetRange;

import com.sihuatech.sqm.spark.bean.LagPhaseBehaviorLog;
import com.sihuatech.sqm.spark.common.Constant;
import com.sihuatech.sqm.spark.util.DateUtil;
import com.sihuatech.sqm.spark.util.PropHelper;

import kafka.serializer.StringDecoder;
import scala.Tuple2;
/**
 * @author zhangsai
 *hiveContext 主要实现 grouping sets
 */
@SuppressWarnings("serial")
public class LagphaseAnalasis {
	private static Logger logger = Logger.getLogger(LagphaseAnalasis.class);
	private static TreeMap<String, Map<String, LagPhaseBehaviorLog>> fifMinuteMap = new TreeMap<String, Map<String,LagPhaseBehaviorLog>>();
	private static TreeMap<String, Map<String, LagPhaseBehaviorLog>> hourMap = new TreeMap<String, Map<String,LagPhaseBehaviorLog>>();
	private final static int DEFAULT_NUM_PARTITIONS = 100;
	private final static long DEFAULT_BATCH_DURATION = 1;
	private static int numPartitions = 0;
	private static AtomicReference<OffsetRange[]> offsetRanges = new AtomicReference<OffsetRange[]>();

	public static void main(String[] args) throws Exception {
		if (null == args || args.length < 2) {
			System.err.println("Usage: LagphaseAnalasis <topics> <numPartitions>\n" +
			          "  <topics> is a list of one or more kafka topics to consume from\n"
			          + " <numPartitions> 重新设置Rdd的分区数量\n\n");
			return;
		}
		String topics = args[0];
		String _numPartitions = args[1];
		String brokers = PropHelper.getProperty("broker.quorum");;
		String _batchDuration = PropHelper.getProperty("EVENT_TREND");
		logger.info("parameters...\n"
				+ "brokers:" + brokers + "\n"
				+ "topics:" + topics + "\n"
				+ "batchDuration:" + _batchDuration + "\n"
				+ "numPartitions:" + _numPartitions + "\n");
		// 转换参数提供使用
		Map<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("metadata.broker.list", brokers);
		Set<String> topicsSet = new HashSet<String>(Arrays.asList(topics.split(",")));
		long batchDuration = NumberUtils.toLong(_batchDuration, DEFAULT_BATCH_DURATION);
		numPartitions = NumberUtils.toInt(_numPartitions, DEFAULT_NUM_PARTITIONS);
		SparkConf sparkConf = new SparkConf().setAppName("LagphaseAnalasis");
		JavaSparkContext ctx = new JavaSparkContext(sparkConf);
		JavaStreamingContext jssc = new JavaStreamingContext(ctx, Durations.minutes(batchDuration));
		logger.info("开始读取内容：");
		// Create direct kafka stream with brokers and topics
		JavaPairInputDStream<String, String> messages = KafkaUtils.createDirectStream(
				jssc,
				String.class,
				String.class,
				StringDecoder.class,
				StringDecoder.class,
				kafkaParams,
				topicsSet
		);
		
		// 获取kafka的offset
		JavaPairDStream<String, String> tmpMessages = messages.transformToPair(new Function<JavaPairRDD<String, String>, JavaPairRDD<String, String>>() {
			@Override
			public JavaPairRDD<String, String> call(JavaPairRDD<String, String> rdd) {
				OffsetRange[] offsets = ((HasOffsetRanges) rdd.rdd()).offsetRanges();
				offsetRanges.set(offsets);
				return rdd;
			}
		});
		
		JavaDStream<String> lines = tmpMessages.map(new Function<Tuple2<String, String>, String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public String call(Tuple2<String, String> tuple2) {
				return tuple2._2();
			}
		});
		logger.debug("+++[Lag]日志验证开始");
		// 校验日志，过滤不符合条件的记录
		JavaDStream<String> filterLines = lines.filter(new Function<String, Boolean>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean call(String line) throws Exception {
				String[] lineArr = line.split(String.valueOf((char) 0x7F), -1);
				if (lineArr.length < 16) {
					return false;
				} else if (!"4".equals(lineArr[0])) {
					return false;
				}
				return true;
			}
		});
		
		filterLines.foreachRDD(new VoidFunction2<JavaRDD<String>, Time>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(JavaRDD<String> rdd, Time time) {
				JavaRDD<LagPhaseBehaviorLog> rowRDD = rdd.flatMap(new FlatMapFunction<String, LagPhaseBehaviorLog>() {
					public Iterable<LagPhaseBehaviorLog> call(String line) {
						List<LagPhaseBehaviorLog> list = new ArrayList<LagPhaseBehaviorLog>();
						String[] lineArr = line.split(String.valueOf((char) 0x7F), -1);
						String startSecond = lineArr[9];
						String freezTime = lineArr[11];
						String hasID = lineArr[1]+lineArr[2];
						//1分钟数据处理，由于每4分钟发一次日志，故只考虑最多卡顿4分钟，indexTime到分钟
						List<String> timeRanges = DateUtil.getMinuteRanges(startSecond, freezTime,Constant.R1);
						if(null != startSecond && null != freezTime && null != timeRanges && timeRanges.size()>0){
							for(String indexTime : timeRanges){
								LagPhaseBehaviorLog lag = new LagPhaseBehaviorLog(hasID, lineArr[2],
										lineArr[3], lineArr[4], lineArr[5], lineArr[6],
										lineArr[7],lineArr[10], lineArr[8],lineArr[9],lineArr[11],indexTime);
								list.add(lag);
							}
						}
						
						//15分钟数据处理，由于每4分钟发一次日志，故当前卡顿可能延续到下一个15分钟区段内，故可分为当前15的时间段和下一个15分钟的时间段，indexTime = indexTime+00 区分一分钟的
						List<String> timeRangesR15 = DateUtil.getMinuteRanges(startSecond, freezTime,Constant.R15);
						if(null != startSecond && null != freezTime && null != timeRangesR15 && timeRangesR15.size()>0){
							for(String fifIndexTime : timeRangesR15){
								Map<String, LagPhaseBehaviorLog> tmpMap = fifMinuteMap.get(fifIndexTime);
								if(null != tmpMap){
									LagPhaseBehaviorLog lag = tmpMap.get(hasID);
									if(null != lag){
										tmpMap.remove(lag);
									}
								}else{
									tmpMap = new HashMap<String, LagPhaseBehaviorLog>();
								}
								LagPhaseBehaviorLog newlag = new LagPhaseBehaviorLog(hasID, lineArr[2],
										lineArr[3], lineArr[4], lineArr[5], lineArr[6],
										lineArr[7],lineArr[10], lineArr[8],lineArr[9],lineArr[11],fifIndexTime);
								tmpMap.put(hasID, newlag);
								fifMinuteMap.put(fifIndexTime, tmpMap);
							}
						}
						// 15分钟的map保留2个
						while (fifMinuteMap.size() > 2) {
							String delKey = fifMinuteMap.firstKey();
							fifMinuteMap.remove(delKey);
							logger.info("删除15分钟map数据：" + delKey);
						}
						
						//60分钟数据处理，由于每4分钟发一次日志，故当前卡顿可能延续到下一个60分钟区段内，故可分为当前60的时间段和下一个60分钟的时间段，indexTime到小时
						List<String> timeRangesR60 = DateUtil.getMinuteRanges(startSecond, freezTime,Constant.R60);
						if(null != startSecond && null != freezTime && null != timeRangesR60 && timeRangesR60.size()>0){
							for(String hourIndexTime : timeRangesR60){
								Map<String, LagPhaseBehaviorLog> tmpMap = hourMap.get(hourIndexTime);
								if(null != tmpMap){
									LagPhaseBehaviorLog lag = tmpMap.get(hasID);
									if(null != lag){
										tmpMap.remove(lag);
									}
								}else{
									tmpMap = new HashMap<String, LagPhaseBehaviorLog>();
								}
								LagPhaseBehaviorLog newlag = new LagPhaseBehaviorLog(hasID, lineArr[2],
										lineArr[3], lineArr[4], lineArr[5], lineArr[6],
										lineArr[7],lineArr[10], lineArr[8],lineArr[9],lineArr[11],hourIndexTime);
								tmpMap.put(hasID, newlag);
								hourMap.put(hourIndexTime, tmpMap);
							}
						}
						// 1小时的map保留2个
						while (hourMap.size() > 2) {
							String delKey = hourMap.firstKey();
							hourMap.remove(delKey);
							logger.info("删除1小时map数据：" + delKey);
						}
						
						for(Entry<String, Map<String, LagPhaseBehaviorLog>> en:fifMinuteMap.entrySet()){
							list.addAll(new ArrayList(fifMinuteMap.get(en.getKey()).values()));
						}
						
						for(Entry<String, Map<String, LagPhaseBehaviorLog>> en:hourMap.entrySet()){
							list.addAll(new ArrayList(hourMap.get(en.getKey()).values()));
						}
						
						return list;
					}
				});
				commenAnalasis(rowRDD);
			}
		});
		jssc.start();
		jssc.awaitTermination();
	}
	
	protected static void commenAnalasis(JavaRDD<LagPhaseBehaviorLog> rdd) {
		int tmpNumPartitions = rdd.getNumPartitions();
		JavaRDD<LagPhaseBehaviorLog> resRDD = null;
		if (tmpNumPartitions < numPartitions) {
			resRDD = rdd.repartition(numPartitions);
		} else if (tmpNumPartitions > numPartitions){
			resRDD = rdd.coalesce(numPartitions);
		}else{
			resRDD = rdd;
		}
		logger.info("resRDD分区数：" + resRDD.getNumPartitions());
		JavaSparkContext jsc = JavaSparkContext.fromSparkContext(resRDD.context());
		HiveContext hiveContext = new HiveContext(jsc);
		DataFrame dataFrame = hiveContext.createDataFrame(resRDD, LagPhaseBehaviorLog.class);
		hiveContext.registerDataFrameAsTable(dataFrame, "LagPhaseLog");
		dataFrame.persist(StorageLevel.MEMORY_AND_DISK_SER());
		long count = dataFrame.count();
		if(count > 0){
			//总次数
			logger.info("+++[Lag]分析开始:"+count);
			lagPhaseDimension(hiveContext);
			logger.info("+++[Lag]分析结束，分析结果记录数如下...\n");
		}
		dataFrame.unpersist();
	}

	public static void lagPhaseDimension(HiveContext hiveContext){
		/*GroupedData dataGroup = dataFrame.cube("provinceID","cityID","platform", "deviceProvider", "fwVersion","indexTime");
		dataGroup.agg(countDistinct("hasID"),countDistinct("probeID")).filter("indexTime is not null").show();
		Row[] stateRow =  dataGroup.agg(countDistinct("hasID"),countDistinct("probeID")).filter("indexTime is not null").collect();*/
		
		String dimens = "((provinceID,cityID,indexTime),(provinceID,platform,indexTime),(provinceID,deviceProvider,indexTime),(provinceID,fwVersion,indexTime),(provinceID,indexTime),(cityID,indexTime),(platform,indexTime),(deviceProvider,indexTime),(fwVersion,indexTime),(indexTime))"; 
		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append("select provinceID,cityID,platform,deviceProvider,fwVersion,indexTime,count(distinct hasID),count(distinct probeID) ");
		sqlSb.append(" from LagPhaseLog GROUP BY provinceID,cityID,platform,deviceProvider,fwVersion,indexTime ");
		sqlSb.append(" GROUPING SETS ").append(dimens);
        DataFrame df = hiveContext.sql(sqlSb.toString());
        df.filter("indexTime is not null").show();
        Row[] stateRow =  df.filter("indexTime is not null").collect();
		HashMap<String, String> playCount = new HashMap<String, String>();
		if (stateRow == null || stateRow.length == 0) {
			return;
		} else {
			for (Row row : stateRow) {
				if(row == null || row.size() == 0){
					continue;
				}
				String  provinceID 	   = null == row.getString(0) ? "ALL" : row.getString(0);
				String  cityID 		   = null == row.getString(1) ? "ALL" : row.getString(1);
				String  platform 	   = null == row.getString(2) ? "ALL" : row.getString(2);
				String  deviceProvider = null == row.getString(3) ? "ALL" : row.getString(3);
				String  fwVersion 	   = null == row.getString(4) ? "ALL" : row.getString(4);
				String key = provinceID + "#" + cityID +  "#" + platform + "#" + deviceProvider + "#" + fwVersion + "\t" + row.getString(5);
				playCount.put(key, row.getLong(6)+"\t"+row.getLong(7));
			}
		}
		if (playCount.size() > 0) {
			/** 卡顿播放次数和用户数入Redis**/
			logger.info("卡顿播放次数和用户数分析开始，map的大小=" + playCount.size());
			//IndexToRedis.playCountToRedis(playCount);
			logger.info("卡顿播放次数和用户数分析结束");
		}
	}

}
