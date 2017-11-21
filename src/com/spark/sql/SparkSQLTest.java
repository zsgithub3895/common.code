package com.spark.sql;

import static org.apache.spark.sql.functions.count;
import static org.apache.spark.sql.functions.countDistinct;
import static org.apache.spark.sql.functions.sum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.GroupedData;
import org.apache.spark.sql.SQLContext;
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

import com.sihuatech.sqm.spark.util.PropHelper;

import kafka.serializer.StringDecoder;
import scala.Tuple2;

public class SparkSQLTest {
	private static Logger logger = Logger.getLogger(SparkSQLTest.class);
	private static final String[] DIMENSION_WARN = {"provinceID", "exportId"};
	private final static int DEFAULT_NUM_PARTITIONS = 100;
	private static int numPartitions = 0;
	private static AtomicReference<OffsetRange[]> offsetRanges = new AtomicReference<OffsetRange[]>();
	public static void main(String[] args) {
		if (null == args || args.length < 2) {
			System.err.println("Usage: LagAnalysis <topic> <numStreams>");
			System.exit(1);
		}
		// 获取参数
		String topic = args[0];
		String numStreamsS = args[1]; // 此参数为接收Topic的线程数，并非Spark分析的分区数
		String brokers = PropHelper.getProperty("broker.quorum");;
		final String batchDurationS = PropHelper.getProperty("LAG_PHASE_TIME");
		logger.info("+++[LAG]parameters...\n"
				+ "topic:" + topic + "\n"
				+ "numStreams:" + numStreamsS + "\n"
				+ "brokers:" + brokers + "\n"
				+ "batchDuration:" + batchDurationS + "\n");
		// 转换参数提供使用
		Map<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("metadata.broker.list", brokers);
		Set<String> topicsSet = new HashSet<String>(Arrays.asList(topic.split(",")));
		long batchDuration = Long.valueOf(batchDurationS);
		numPartitions = NumberUtils.toInt(numStreamsS, DEFAULT_NUM_PARTITIONS);
		// spark任务初始化
		SparkConf sparkConf = new SparkConf().setAppName("LAG");
		sparkConf.setMaster("local[*]");
		JavaSparkContext ctx = new JavaSparkContext(sparkConf);
		JavaStreamingContext jssc = new JavaStreamingContext(ctx, Durations.minutes(batchDuration));
		logger.info("+++[LAG]开始读取内容：");
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
		// 过滤不符合规范的记录
		JavaDStream<String> filterLines = lines.filter(new Function<String, Boolean>() {
			@Override
			public Boolean call(String line) throws Exception {
				System.out.println("++++++++++++++++"+line);
				String[] lineArr = line.split(String.valueOf((char)0x7F), -1);
				if (!"4".equals(lineArr[0])) {
					return false;
				}
				return true;
			}
		});
		
		JavaDStream<LagPhaseBehaviorLog> objs = filterLines.map(new Function<String, LagPhaseBehaviorLog>() {
			private static final long serialVersionUID = 1L;
			public LagPhaseBehaviorLog call(String word) {
				LagPhaseBehaviorLog lag = null;
				if (StringUtils.isNotBlank(word)) {
					String[] lineArr = word.split(String.valueOf((char) 0x7F), -1);
					String kpiUtcSec = lineArr[8].trim().substring(0, 12);
					lag = new LagPhaseBehaviorLog(
							Integer.valueOf(lineArr[0]), lineArr[1], lineArr[2],
							lineArr[3], lineArr[4], lineArr[5], lineArr[6],
							lineArr[7],lineArr[10], kpiUtcSec);
				}
				return lag;
			}
			
		});
		
		// 校验日志，过滤不符合条件的记录,处理数据
		JavaDStream<LagPhaseBehaviorLog> playResponseLogs = objs.filter(new Function<LagPhaseBehaviorLog, Boolean>() {
			@Override
			public Boolean call(LagPhaseBehaviorLog play) throws Exception {
				if (null==play) {
					return false;
				}
				return true;
			}
		});
		
		
		playResponseLogs.foreachRDD(new VoidFunction2<JavaRDD<LagPhaseBehaviorLog>, Time>() {
			
			private static final long serialVersionUID = 1L;
			@Override
			public void call(JavaRDD<LagPhaseBehaviorLog> rdd, Time time) {
				
				logger.info("+++[LAG]开始!");
				logger.info("+++[LAG]RDD分区数：" + rdd.getNumPartitions());
				
				SQLContext sqlContext = SQLContext.getOrCreate(rdd.context());
				DataFrame dataFrame = sqlContext.createDataFrame(rdd, LagPhaseBehaviorLog.class);
				dataFrame.registerTempTable("LagPhaseBehaviorLog");
				dataFrame.persist(StorageLevel.MEMORY_AND_DISK_SER());
				long count = dataFrame.count();
				logger.info("+++[LAG]记录数:" + count);
				if (count > 0) {
					dataFrame = dataFrame.filter("exportId<10");
					streamAndPlayByDimension(dataFrame);
				}
				dataFrame.unpersist();
				}
		});
		jssc.start();
		jssc.awaitTermination();
	}
	
	/**
	 * 4|3333|123000006|cwj2|huashu2|jiangsu2|najing|2.1.7.13|20171114152500|20171114152500|1|100|www.baidu.com|1|192.168.1.23|RESERVE1|RESERVE2
4|3333|123000006|cwj2|huashu2|jiangsu2|nanjing|2.1.7.13|20171114152500|20171114152500|11|100|www.baidu.com|1|192.168.1.23|RESERVE1|RESERVE
	 */
	private static void streamAndPlayByDimension(DataFrame dataFrame) {
			GroupedData data = dataFrame.cube(DIMENSION_WARN[0],DIMENSION_WARN[1],"kPIUTCSec");
			data.agg(countDistinct("hasID"),count("hasID"),sum("hasID")).filter("kPIUTCSec is not null").show();
			/*Row[] stateRow = data.agg(countDistinct("hasID"),countDistinct("probeID")).filter("KPIUTCSec is not null").collect();
			if (stateRow == null || stateRow.length == 0) {
				return;
			} else {
				for (Row row : stateRow) {
					if(row == null || row.size() == 0){
						continue;
					}
					String  pID = row.getString(0);
					String  sourceIP = row.getString(1);
					if(null == pID){
						pID  = "ALL";
					}
					if(null == sourceIP){
						sourceIP  = "ALL";
					}
					String key = pID + "#"+ sourceIP + "\t"  + row.getString(2);
					logger.info("+++++++++++++++++++++++++++"+pID + "#"+ sourceIP +"#"+row.getString(2)+"|"+row.getLong(3)+"|"+row.getLong(4));
				}*/
		  }
}