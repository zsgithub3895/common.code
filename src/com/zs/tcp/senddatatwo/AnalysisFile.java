package com.zs.tcp.senddatatwo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AnalysisFile {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	static DecimalFormat nf = new DecimalFormat("0.00");
	public static void main(String[] args) throws IOException, ParseException {
		Map<String, String> hasIDMap = new HashMap<String, String>();
		Map<String, String> probeIDMap = new HashMap<String, String>();
		String startDateTime = "20170309000000";
		String endDateTime   = "20170310000000";

		if (args != null && args.length >= 2) {
			startDateTime = args[0];
			endDateTime = args[1];
		} else {
			System.out.println("请给出开始和结束时间参数（yyyyMMddHHmmss）");
		}

		// 文件时间提前和延后1小时
		long start = sdf.parse(startDateTime).getTime() / 1000-600;
		long end = sdf.parse(endDateTime).getTime() / 1000+600;
		System.out.println(startDateTime + ":" + start + "," + endDateTime + ":" + end);

		BufferedReader br = null;
		String filePath = null;
		// filePath =
		// "/Users/chenyunjie/Downloads/securecrtdownload/data/EVQMWorkingDir/CacheRa";
		filePath = "/data/EVQMWorkingDir/CacheRa";

		File dir = new File(filePath);

		File[] files = dir.listFiles();
		long fileCount = 0;
		for (File file : files) {
			String fileName = file.getName();
			if (!fileName.endsWith(".csv")) {
				continue;
			}
			if (fileName.length() != 17) {
				continue;
			}
			long timestamp = Long.parseLong(fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf(".")));
			if (timestamp < start || timestamp > end) {
				continue;
			}
			fileCount++;
			// System.out.println("Progress:第" + ++fileCount + "个文件：" +
			// file.getName());

			br = new BufferedReader(new FileReader(file));
			if (br != null) {
				String tmp = null;
				while ((tmp = br.readLine()) != null) {
					char c = 0x7F;
					String[] ex = tmp.split(String.valueOf(c), -1);
					if(ex.length > 40){
					String hasId = ex[1];
					String probeID = ex[4];
					// String provinceID = ex[7];
					String hasType = ex[10];
					// String playSecond = ex[12];
					String freezTime = ex[14];
					String kpiutcsec = ex[23];
					String downSeconds = ex[24];
					String value = kpiutcsec + "|" + probeID + "|" + freezTime + "|" + downSeconds;
				   if("20170309000000".compareTo(kpiutcsec) <= 0 &&
					 "20170310000000".compareTo(kpiutcsec) > 0){
					if ("3".equals(ex[0]) && "23".equals(ex[7])) {
							String vv = hasIDMap.get(hasId);
							if (null != vv && hasIDMap.containsKey(hasId)) {
								String kpiutcsecTwo = vv.split("\\|")[0];
								if (kpiutcsec.compareTo(kpiutcsecTwo) > 0) {
									hasIDMap.put(hasId, value);
								}
							} else {
								hasIDMap.put(hasId, value);
							}
					}
				   }
				}
				}
			}
		}
		System.out.println("总文件数：" + fileCount);
		
		  for (Entry<String, String> en : hasIDMap.entrySet()) {
			String kpiutcsecTwo = en.getValue().split("\\|")[0];
			String probeIDTwo = en.getValue().split("\\|")[1];
			long freezTimeTwo = Long.valueOf(en.getValue().split("\\|")[2]);
			long downSecondsTwo = Long.valueOf(en.getValue().split("\\|")[3]);
			String v = probeIDMap.get(probeIDTwo);
			if (null != v && probeIDMap.containsKey(probeIDTwo)) {
				String time = v.split("\\|")[0];
				long vfreeze = Long.valueOf(v.split("\\|")[1]);
				long vdown = Long.valueOf(v.split("\\|")[2]);
				freezTimeTwo += vfreeze;
				downSecondsTwo += vdown;
				probeIDMap.put(probeIDTwo, time + "|" + freezTimeTwo + "|" + downSecondsTwo);
			} else {
				probeIDMap.put(probeIDTwo, kpiutcsecTwo + "|" + freezTimeTwo + "|" + downSecondsTwo);
			}
		}
		int t1=0;
		int t2=0;
		int t3=0;
		int t4=0;
		for(Entry<String, String> enen:probeIDMap.entrySet()){
			long r1= Long.valueOf(enen.getValue().split("\\|")[1]);
			long r2= Long.valueOf(enen.getValue().split("\\|")[2]);
			double result = (r1*1.0d)/r2;
			if(result == 0){
				t1 ++;//无故障
			}
			if(result > 0 && result<=10000){
				t2 ++;//偶尔
			}
			if(result>10000 && result<=50000){
				t3 ++;//较多
			}
			if(result > 50000){
				t4++;//严重
			}
			
		}
		System.out.println("sum(无故障用户)="+t1);
		System.out.println("sum(偶尔故障用户)="+t2);
		System.out.println("sum(较多故障用户)="+t3);
		System.out.println("sum(严重故障用户)="+t4);
		br.close();
	}

	public void getFilter8ProbeID(String tmp, String startDateTime, String endDateTime, int count) {
		char c = 0x7f;
		String[] s = tmp.split(String.valueOf(c));
		if (s.length > 40) {
			if ("3".equals(s[0]) && "23".equals(s[7])) {
				String probeID = s[4];
				String kpiutcsec = s[23];
				/*
				 * String hasID= s[1]; String freezeTime= s[14]; String
				 * downSeconds= s[24];
				 */
				if (kpiutcsec.compareTo(startDateTime) >= 0 && kpiutcsec.compareTo(endDateTime) < 0) {
					if (probeID.length() != 8) {
						System.out.println("++++" + probeID);
					} else if (probeID.length() == 8) {
						count++;
					}
				}
			}
		}
	}

}
