package com.zs.tcp.senddata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

//3/28 21:00:00
public class HLSFour {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	static DecimalFormat nf = new DecimalFormat("0.00");

	public static void main(String[] args) throws Exception {
		Map<String, String> hasIDandprobeID = new HashMap<String, String>();
		Map<String, String> hasIDandprobeIDTwo = new HashMap<String, String>();
		Map<String, String> probeIDMap = new HashMap<String, String>();
		Map<Long, Long> result = new HashMap<Long, Long>();
		String startDateTime = "20170328200000";
		String endDateTime = "20170328220000";

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
			if (br != null) {
				String tmp = null;
				while ((tmp = br.readLine()) != null) {
					char c = 0x7F;
					String[] ex = tmp.split(String.valueOf(c), -1);
					if (ex.length > 24 && "3".equals(ex[0])) {
						String hasId = ex[1];
						String probeID = ex[4];
						String provider = ex[5];
						String platform = ex[6];
						String provinceID = ex[7];
						String cityID = ex[8];
						String fwversion = ex[9];
						String hasType = ex[10];
						String kpiutcsec = ex[23];
						String downSeconds = ex[24];
						// String key =
						// provinceID+"#"+cityID+"#"+platform+"#"+provider+"#"+fwversion+"#9#861";
						String key = provinceID + "#" + cityID + "#" + platform + "#" + provider + "#" + fwversion + "#"
								+ hasType;
						String value = kpiutcsec + "|" + probeID + "|" + hasType + "|" + downSeconds;
							if ("20170328200000".compareTo(kpiutcsec) <= 0
									&& "20170328210000".compareTo(kpiutcsec) > 0) {
								//System.out.println("before:" + probeID + "|" + hasId + "|" + value);
								String vv = hasIDandprobeID.get(probeID + hasId);
								if (null != vv) {
									String kpiutcsecTwo = vv.split("\\|")[0];
									if (kpiutcsec.compareTo(kpiutcsecTwo) > 0) {
										hasIDandprobeID.put(probeID + hasId, value);
									}
								} else {
									hasIDandprobeID.put(probeID + hasId, value);
								}
							}

							if ("20170328210000".compareTo(kpiutcsec) <= 0
									&& "20170328220000".compareTo(kpiutcsec) > 0) {
									String vv = hasIDandprobeIDTwo.get(probeID + hasId);
									if (null != vv) {
										String kpiutcsecTwo = vv.split("\\|")[0];
										if (kpiutcsec.compareTo(kpiutcsecTwo) > 0) {
											hasIDandprobeIDTwo.put(probeID + hasId, value);
										}
									} else {
										hasIDandprobeIDTwo.put(probeID + hasId, value);
									}
							}
					}
				}

			}
		}
		
		
		for (Entry<String, String> en : hasIDandprobeIDTwo.entrySet()) {
			long hasType = Long.valueOf(en.getValue().split("\\|")[2]);
			long downSecondsTwo = Long.valueOf(en.getValue().split("\\|")[3]);
			String key = en.getKey();
			String value = hasIDandprobeID.get(key);
			if (null != value) {
				long downSecondsOne = Long.valueOf(value.split("\\|")[3]);
				downSecondsTwo -= downSecondsOne;
				probeIDMap.put(key, hasType + "|" + downSecondsTwo);
			} else {
				probeIDMap.put(key, hasType + "|" + downSecondsTwo);
			}
		}

		for (Entry<String, String> enen : probeIDMap.entrySet()) {
			long typ = Long.valueOf(enen.getValue().split("\\|")[0]);
			long sumDownbytes = Long.valueOf(enen.getValue().split("\\|")[1]);
			Long ll = result.get(typ);
			if (null != ll) {
				sumDownbytes += ll;
				result.put(typ, sumDownbytes);
			} else {
				result.put(typ, sumDownbytes);

			}
		}

		for (Entry<Long, Long> enen : result.entrySet()) {
			System.out.println("+++type=" + enen.getKey() + "    value=" + enen.getValue());
		}
		
		br.close();
	}
}
