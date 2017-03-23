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

public class AnalysisBuss {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	static DecimalFormat nf = new DecimalFormat("0.00");
	public static void main(String[] args) throws IOException, ParseException {
		Map<String, String> hasIDMap = new HashMap<String, String>();
		Map<Long, Long> probeIDMap = new HashMap<Long, Long>();
		String startDateTime = "20170227130000";
		String endDateTime = "20170227140000";

		if (args != null && args.length >= 2) {
			startDateTime = args[0];
			endDateTime = args[1];
		} else {
			System.out.println("请给出开始和结束时间参数（yyyyMMddHHmmss）");
		}

		// 文件时间提前和延后1小时
		// long start = sdf.parse(startDateTime).getTime() / 1000 - 600;
		long start = sdf.parse(startDateTime).getTime() / 1000-600;
		// long end = sdf.parse(endDateTime).getTime() / 1000 + 600;
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
					String value = kpiutcsec + "|" + probeID + "|" + hasType + "|" + downSeconds;
				   if("20170226190000".compareTo(kpiutcsec) <= 0 &&
					 "20170226200000".compareTo(kpiutcsec) > 0){
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
			long hasType = Long.valueOf(en.getValue().split("\\|")[2]);
			long downSecondsTwo = Long.valueOf(en.getValue().split("\\|")[3]);
			Long v = probeIDMap.get(hasType);
			if (null!=v && probeIDMap.containsKey(hasType)) {
				downSecondsTwo += v;
				probeIDMap.put(hasType, downSecondsTwo);
			} else {
				probeIDMap.put(hasType,downSecondsTwo);
			}
		}
		for(Entry<Long, Long> enen:probeIDMap.entrySet()){
			long type= enen.getKey();
			long sumDownbytes= enen.getValue();
			if(type == 4){
				System.out.println("TS点播数："+sumDownbytes);
			}
			if(type == 1){
				System.out.println("HLS直播数："+sumDownbytes);
			}
			if(type == 2){
				System.out.println("HLS点播数："+sumDownbytes);
			}
			if(type == 3){
				System.out.println("MP4点播数："+sumDownbytes);
			}
			if(type == 5){
				System.out.println("FLV播数："+sumDownbytes);
			}
		}
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
