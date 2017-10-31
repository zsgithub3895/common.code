package com.zs.tcp.senddata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class EPGTest {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	static DecimalFormat nf = new DecimalFormat("0.00");

	public static void main(String[] args) throws Exception {
		Map<String, String> mapServerIP = new HashMap<String, String>();
		String startDateTime = "20170405080000";
		String endDateTime = "20170405090000";
		if (args != null && args.length >= 2) {
			startDateTime = args[0];
			endDateTime = args[1];
		} else {
			System.out.println("请给出开始和结束时间参数（yyyyMMddHHmmss）");
		}
		// 文件时间提前和延后1小时
		long start = sdf.parse(startDateTime).getTime() / 1000 - 600;
		long end = sdf.parse(endDateTime).getTime() / 1000 + 600;
		System.out.println(startDateTime + ":" + start + "," + endDateTime + ":" + end);
		BufferedReader br = null;
		String filePath = null;
		filePath = "/data/EVQMWorkingDir/CacheRa";
		File dir = new File(filePath);
		File[] files = dir.listFiles();
		long rangeOne = 0;
		long rangeTwo = 0;
		long rangeThree = 0;
		long rangeFour = 0;
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
			br = new BufferedReader(new FileReader(file));
			if (null != br) {
				String tmp = null;
				while ((tmp = br.readLine()) != null) {
					char c = 0x7F;
					String[] ex = tmp.split(String.valueOf(c), -1);
					if (ex.length > 16 && "6".equals(ex[0]) && "23".equals(ex[3])) {
						String probeID = ex[1];
						/*
						 * String platform = ex[2]; String provinceID = ex[3];
						 * String cityID = ex[4]; String deviceProvider = ex[5];
						 * String fwversion = ex[6]; String hasType = ex[8];
						 */
						String serveIP = ex[7];
						String kpiutcsec = ex[9];
						long requests = Integer.valueOf(ex[11]);
						long httpRspTime = Integer.valueOf(ex[12]);
						String valueOne =serveIP+"|"+kpiutcsec + "|" + requests + "|" + httpRspTime;
						System.out.println(valueOne);
						/*
						String valueOne = kpiutcsec + "|" + requests + "|" + httpRspTime;
						String value = mapServerIP.get(serveIP);
						if (kpiutcsec.compareTo(startDateTime) >= 0 && kpiutcsec.compareTo(endDateTime) < 0) {
							if (null != value) {
								String time = value.split("\\|")[0];
								if (kpiutcsec.compareTo(time) > 0) {
									mapServerIP.put(serveIP, valueOne);
								}
							} else {
								mapServerIP.put(serveIP, valueOne);
							}
						}*/

						if (kpiutcsec.compareTo(startDateTime) >= 0 && kpiutcsec.compareTo(endDateTime) < 0) {
							if (httpRspTime >= 0 && httpRspTime < 100000) {
								rangeOne += requests;
							}
							if (httpRspTime >= 100000 && httpRspTime < 300000) {
								rangeTwo += requests;
							}
							if (httpRspTime >= 300000 && httpRspTime < 2000000) {
								rangeThree += requests;
							}
							if (httpRspTime >= 2000000) {
								rangeFour += requests;
							}
						}
					}
				}

			}
		}

		/*for(Entry<String, String> e:mapServerIP.entrySet()){
			String[] valueAll = e.getValue().split("\\|");
			long requests2 = Integer.valueOf(valueAll[1]);
			long httpRspTime2 = Integer.valueOf(valueAll[2]);
			 if(httpRspTime2>=0 && httpRspTime2<100000){
				  rangeOne+=requests2; 
				  } 
			 if(httpRspTime2>=100000 &&httpRspTime2<300000){ 
				 rangeTwo+=requests2; 
				 }
		     if(httpRspTime2>=300000 && httpRspTime2<2000000){
				 rangeThree+=requests2; 
				 } 
		     if(httpRspTime2>=2000000){
				 rangeFour+=requests2; 
		     }
	}*/

		System.out.println("rangeOne=" + rangeOne + "   rangeTwo=" + rangeTwo);
		System.out.println("rangeThree=" + rangeThree + "   rangeFour=" + rangeFour);
}
}