package com.zs.tcp.senddata;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AnalysisFile {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static void main(String[] args) throws IOException, ParseException {
		String provinceID = "23";
		String startDateTime = "20170225000000";
		String endDateTime = "201702251100000";
		
		if (args != null && args.length >= 2) {
			startDateTime = args[0];
			endDateTime = args[1];
		} else {
			System.out.println("请给出开始和结束时间参数（yyyyMMddHHmmss）");
		}
		
		// 文件时间提前和延后1小时
		//long start = sdf.parse(startDateTime).getTime() / 1000 - 600;
		long start = sdf.parse(startDateTime).getTime() / 1000;
		//long end = sdf.parse(endDateTime).getTime() / 1000 + 600;
		long end = sdf.parse(endDateTime).getTime() / 1000;
		System.out.println(startDateTime + ":" + start + "," + endDateTime + ":" + end);
		
		BufferedReader br = null;
		String filePath = null;
//		filePath = "/Users/chenyunjie/Downloads/securecrtdownload/data/EVQMWorkingDir/CacheRa";
		filePath = "/data/EVQMWorkingDir/CacheRa";
		
		File dir = new File(filePath);
		
		File[] files = dir.listFiles();
		System.out.println("总文件数：" + files.length);
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
			System.out.println("Progress:第" + ++fileCount + "个文件：" + file.getName());
			
			br = new BufferedReader(new FileReader(file));
			if (br != null) {
				String tmp = null;
				while ((tmp = br.readLine()) != null) {
					//PlayAnalysis.processLine(tmp, provinceID, startDateTime, endDateTime, fileName);
//					InfoAnalysis.processLine(tmp, provinceID, startDateTime, endDateTime, fileName);
//					StateAnalysis.processLine(tmp, provinceID, startDateTime, endDateTime, fileName);
					char c = 0x7f;
					String[] s= tmp.split(String.valueOf(c));
					 if(s.length>40){
						   if("3".equals(s[0]) && "23".equals(s[7])){
							   String probeID= s[4];
							   /*String kpiutcsec= s[23];
							   String hasID= s[1];
							   String freezeTime= s[14];
							   String downSeconds= s[24];*/
							   if(probeID.length()!=8){
								   System.out.println("++++"+probeID);
							   }
				    	}
					   }
				}
			}
		}
		br.close();
	}
}
