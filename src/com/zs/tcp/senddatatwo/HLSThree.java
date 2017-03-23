package com.zs.tcp.senddatatwo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HLSThree {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	static DecimalFormat nf = new DecimalFormat("0.00");
	public static void main(String[] args) throws Exception {
		File conf = new File("hasids.csv");
		String path=conf.getAbsolutePath();
		/*if (conf.exists()) {
			path=conf.getAbsolutePath();
		} else {
			path = HLSThree.class.getClassLoader().getResource("hasids.csv").getPath();
		}*/
		File outFile9 =new File("C:\\Users\\sai\\Desktop\\hls122result.csv");
		//File outFile10 =new File("C:\\Users\\sai\\Desktop\\hls10.csv");
		BufferedWriter bwZS9 = new BufferedWriter(new FileWriter(outFile9));
		BufferedReader hasidBr =new BufferedReader(new FileReader(path));
		String line=null;
		/*while((line=hasidBr.readLine())!=null){
			System.out.println(line);
		}*/
		
		Map<String, String> hasIDandprobeID = new HashMap<String, String>();
		String startDateTime = "20170222120000";
		String endDateTime = "20170222140000";
		
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
		//filePath = "/data/EVQMWorkingDir/CacheRa";
		filePath = "D:\\QQMiniDL\\20170222\\data\\EVQMWorkingDir\\CacheRa";
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
					if(ex.length > 24){
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
					String key = provinceID+"#"+cityID+"#"+platform+"#"+provider+"#"+fwversion+"#9#861";
					if("23#377#3#47#2.1.7.12_M3#9#861".equals(key)){
						
						//23#377#3#47#2.1.7.12_M3#9#861|18020550424356547
						//3#23#377#3#47#2.1.7.12_M3#9#861#861|18020550424356547|2
						if("20170222120000".compareTo(kpiutcsec) <= 0 && "20170222140000".compareTo(kpiutcsec) > 0){
						if("2".equals(hasType)||"9".equals(hasType)){
							bwZS9.write(fileName);
							bwZS9.newLine();
							bwZS9.write(tmp);
							bwZS9.newLine();
						}
						}
						
						//bwZS9.write(ex[0]+"#"+key+"#"+downSeconds+"|"+probeID+hasId+"|"+hasType);
						//bwZS9.newLine();
					}
					String value = kpiutcsec + "|" + probeID + "|" + hasType + "|" + downSeconds;
					/*if ("9".equals(hasType) && "3".equals(ex[0]) && "23".equals(provinceID)&& "377".equals(cityID) && "3".equals(platform) && "47".equals(provider)&&"2.1.7.12_M3".equals(fwversion)) {
						if("20170222120000".compareTo(kpiutcsec) <= 0 && "20170222130000".compareTo(kpiutcsec) > 0){
							System.out.println("before:"+probeID+"|"+hasId+"|"+value);
							//String ss =probeID+hasId;
					    //"provinceID", "cityID", "platform", "deviceProvider", "fwVersion",type,hasid,hour,value
						//23#377#3#47#2.1.7.12_M3#9#18020550424356547#HOUR#861
						
							String vv = hasIDandprobeID.get(probeID+hasId);
							if (null != vv) {
								String kpiutcsecTwo = vv.split("\\|")[0];
								if (kpiutcsec.compareTo(kpiutcsecTwo) > 0) {
									hasIDandprobeID.put(probeID+hasId, value);
								}
							} else {
								hasIDandprobeID.put(probeID+hasId, value);
							}
				   }
				}*/
				}	
			}
	
		   /*int count=0;
		   while((line=hasidBr.readLine())!=null){
				//System.out.println("before:"+en.getKey()+"|"+en.getValue());
				for(Entry<String, String> en:hasIDandprobeID.entrySet()){
					if(line.equals(en.getKey())){
						count++;
						System.out.println("before:"+en.getKey()+"|"+en.getValue());
					}
				}
			}*/
		//System.out.println("count="+count);
		br.close();
		}
}
	}
}
