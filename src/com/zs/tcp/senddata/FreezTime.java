package com.zs.tcp.senddata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class FreezTime {
	static Map<String, Long> maxMap = new HashMap<String, Long>();
	public static void main(String[] args) {
				try {
					File logDir = new File("D:\\playRequest220");
					if (!logDir.isDirectory())
						System.exit(1);
					int count = 0;
					long sum = 0;
					for (File logFile : logDir.listFiles()) {
						BufferedReader reader = new BufferedReader(new FileReader(logFile));
						String log;
						while ((log = reader.readLine()) != null) {
							String[] fields = log.split(String.valueOf((char) 0x7F),-1);
							if (fields.length>23) {//"3".equals(fields[0]) && "23".equals(fields[7]) &&
								count++;
								String timeFields= fields[23].replace("-", "").replace(":", "").replace(" ", "");
								String hasId = fields[1];
								Long freezeTime=0l;
								if(StringUtils.isNotBlank(fields[14])){
									freezeTime = Long.valueOf(fields[14]);
								}
								maxMapAfter(timeFields,hasId,freezeTime);
						}
						}
					}
					
					for (Entry<String, Long> en : maxMap.entrySet()) {
						long max = en.getValue();
						sum += max;
					}
					System.out.println("广西的freezeTime=" + sum + ",换算单位h=" + (sum / 1000 / 1000 / 3600));
					maxMap.clear();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
	}

	private static Map<String, Long> maxMapAfter(String timeFields,String hasId,Long freezeTime) {
		if("20170221000000".compareTo(timeFields) <= 0 && "20170222000000".compareTo(timeFields) > 0 ){
				if (maxMap.containsKey(hasId)) {
					long value = maxMap.get(hasId);
					if (value < freezeTime) {
						maxMap.put(hasId, freezeTime);
					}
				} else {
					maxMap.put(hasId, freezeTime);
				}
			}
		return maxMap;
	}
	
}
