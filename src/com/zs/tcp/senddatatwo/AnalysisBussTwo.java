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

public class AnalysisBussTwo {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	static DecimalFormat nf = new DecimalFormat("0.00");
	public static void main(String[] args) throws IOException, ParseException {
		Map<String, String> hasIDandprobeID = new HashMap<String, String>();
		Map<String, String> hasIDandprobeIDTwo = new HashMap<String, String>();
		Map<String, String> probeIDMap = new HashMap<String, String>();
		Map<Long, Long> result = new HashMap<Long, Long>();
		String startDateTime = "20170226180000";
		String endDateTime = "20170226200000";

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
				   if("20170226180000".compareTo(kpiutcsec) <= 0 &&
					 "20170226190000".compareTo(kpiutcsec) > 0){
					if ("3".equals(ex[0]) && "23".equals(ex[7])) {
							String vv = hasIDandprobeID.get(hasId+probeID);
							if (null != vv && hasIDandprobeID.containsKey(hasId+probeID)) {
								String kpiutcsecTwo = vv.split("\\|")[0];
								if (kpiutcsec.compareTo(kpiutcsecTwo) > 0) {
									hasIDandprobeID.put(hasId+probeID, value);
								}
							} else {
								hasIDandprobeID.put(hasId+probeID, value);
							}
						}
				   }
				   
				   if("20170226190000".compareTo(kpiutcsec) <= 0 &&
							 "20170226200000".compareTo(kpiutcsec) > 0){
							if ("3".equals(ex[0]) && "23".equals(ex[7])) {
									String vv = hasIDandprobeIDTwo.get(hasId+probeID);
									if (null != vv && hasIDandprobeIDTwo.containsKey(hasId+probeID)) {
										String kpiutcsecTwo = vv.split("\\|")[0];
										if (kpiutcsec.compareTo(kpiutcsecTwo) > 0) {
											hasIDandprobeIDTwo.put(hasId+probeID, value);
										}
									} else {
										hasIDandprobeIDTwo.put(hasId+probeID, value);
									}
								}
						   }
				   
				}
				}
			}
		}
		
		System.out.println("总文件数：" + fileCount);
		
		  for (Entry<String, String> en : hasIDandprobeIDTwo.entrySet()) {
			long hasType = Long.valueOf(en.getValue().split("\\|")[2]);
			long downSecondsTwo = Long.valueOf(en.getValue().split("\\|")[3]);
			String key =en.getKey();
			String value = hasIDandprobeID.get(key);
			if(null!=value){
				long downSecondsOne = Long.valueOf(value.split("\\|")[3]);
				downSecondsTwo -= downSecondsOne;
				probeIDMap.put(key,hasType+"|"+downSecondsTwo);
			}else{
				probeIDMap.put(key,hasType+"|"+downSecondsTwo);
			}
		}
		  
		for(Entry<String, String> enen:probeIDMap.entrySet()){
			long typ= Long.valueOf(enen.getValue().split("\\|")[0]);
			long sumDownbytes= Long.valueOf(enen.getValue().split("\\|")[1]);
			  Long ll = result.get(typ);
			  if(null !=ll){
				  sumDownbytes += ll;
				  result.put(typ, sumDownbytes);
			  }else{
				  result.put(typ, sumDownbytes);
			  }
		}
		
		for(Entry<Long, Long> enen:result.entrySet()){
			long type=enen.getKey();
			long DowntesResult=enen.getValue();
			if(type == 1){
				System.out.println("HLS直播数："+DowntesResult);
			}
			if(type == 2){
				System.out.println("HLS点播数："+DowntesResult);
			}
			if(type == 3){
				System.out.println("MP4点播数："+DowntesResult);
			}
			if(type == 4){
				System.out.println("TS点播数："+DowntesResult);
			}
			if(type == 5){
				System.out.println("FLV播数："+DowntesResult);
			}
			if(type == 9){
				System.out.println("HLS回看："+DowntesResult);
			}
		}
		br.close();
		}
}
