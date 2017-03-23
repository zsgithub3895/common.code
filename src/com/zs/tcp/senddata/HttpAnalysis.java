package com.zs.tcp.senddata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
public class HttpAnalysis {
	static Map<String, String> promgeramMap = new HashMap<String, String>();
	static Map<String, Long> maxMapBefore = new HashMap<String, Long>();
	static Map<String, Long> maxMap = new HashMap<String, Long>();
	public static void main(String[] args) throws IOException {
		playRequest();
	}
	private static void playRequest() throws IOException{
		//File outFile =new File("D:\\playrequestResult.csv");
		//BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
				try {
					File logDir = new File("D:\\playRequest221");
					if (!logDir.isDirectory())
						System.exit(1);
					int count = 0;
					long sum = 0;
					for (File logFile : logDir.listFiles()) {
						 //System.out.println("开始处理日志文件" + logFile.getName());
						BufferedReader reader = new BufferedReader(new FileReader(logFile));
						String log;
						while ((log = reader.readLine()) != null) {
							String[] fields = log.split(String.valueOf((char) 0x7F),-1);
							if (fields.length>23) {//"3".equals(fields[0]) && "23".equals(fields[7]) &&
								count++;
								String timeFields= fields[23].replace("-", "").replace(":", "").replace(" ", "");
								String hasId = fields[1];
								//String programName = fields[3];
								//String timeFields = fields[23];
								Long downBytes=0l;
								if(StringUtils.isNotBlank(fields[17])){
									downBytes = Long.valueOf(fields[17]);
								}
								//getMaxHasId(timeFields,hasId,downBytes);
								maxMapAfter(timeFields,hasId,downBytes);
								//beforeMap(timeFields,hasId,downBytes,programName);
							}
						}
					}
					
					/*System.out.println("广西桂林市的日志条数：" + count);
					System.out.println("count 尺寸=" + maxMap.size());*/
					System.out.println("广西桂林市的日志条数：" + count);
					System.out.println("count 尺寸=" + maxMapBefore.size());

					for (Entry<String, Long> en : maxMap.entrySet()) {
						String hasIDTwo = en.getKey();
						long tmpdownByteTwo =0;
						if(null != maxMapBefore.get(hasIDTwo)){
							tmpdownByteTwo = maxMapBefore.get(hasIDTwo);
						}
						
						long max = en.getValue()-tmpdownByteTwo;
						sum += max;
					}
					/*for (Entry<String, String> en : promgeramMap.entrySet()) {
						bw.write(en.getKey()+"|"+en.getValue());
			    		bw.newLine();
					}*/
					System.out.println("广西桂林市的HTTP总流量 sum=" + sum + ",换算单位G=" + (sum / 1024 / 1024 / 1024));
					maxMapBefore.clear();
					maxMap.clear();
					//promgeramMap.clear();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
	}
	
	/*private static Map<String, Long> getMaxHasId(String timeFields,String hasId,Long downBytes) {
		if("20170219000000".compareTo(timeFields) <= 0 && "20170220010000".compareTo(timeFields) > 0 ){
				if (maxMapBefore.containsKey(hasId)) {
					long value = maxMapBefore.get(hasId);
					if (value < downBytes) {
						maxMapBefore.put(hasId, downBytes);
					}
				} else {
					maxMapBefore.put(hasId, downBytes);
				}
		}
		return maxMapBefore;
	}*/
	
	/*private static Map<String, Long> beforeMap(String timeFields,String hasId,Long downBytes,String programName) {
		if("20170216000000".compareTo(timeFields) <= 0 && "20170216010000".compareTo(timeFields) > 0 ){
				if (maxMapBefore.containsKey(hasId)) {
					long value = maxMapBefore.get(hasId);
					if (value < downBytes) {
						maxMapBefore.put(hasId, downBytes);
						promgeramMap.put(hasId,programName);
					}
				} else {
					maxMapBefore.put(hasId, downBytes);
					promgeramMap.put(hasId,programName);
				}
		}
		return maxMapBefore;
	}*/
	
	private static Map<String, Long> maxMapAfter(String timeFields,String hasId,Long downBytes) {
		if("20170220000000".compareTo(timeFields) <= 0 && "20170220012000".compareTo(timeFields) > 0 ){
				if (maxMap.containsKey(hasId)) {
					long value = maxMap.get(hasId);
					if (value < downBytes) {
						maxMap.put(hasId, downBytes);
					}
				} else {
					maxMap.put(hasId, downBytes);
				}
			}
		return maxMap;
	}
	
	
}