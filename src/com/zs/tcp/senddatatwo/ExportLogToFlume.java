package com.zs.tcp.senddatatwo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ExportLogToFlume {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	public static void main(String[] args) {
		String startDateTime = "20170222120000";
		String endDateTime = "20170222140000";
		try {
			File logDir = new File("D:\\QQMiniDL\\20170222\\data\\EVQMWorkingDir\\CacheRa");
			if (!logDir.isDirectory())
				System.exit(1);

			// 客户端请求与10.223.138.124在20006端口建立TCP连接
			//Socket client = new Socket("10.223.138.141", 5145);
			Socket client = new Socket("10.223.138.141", 5143);
			// 获取Socket的输出流，用来发送数据到服务端
			PrintStream out = new PrintStream(client.getOutputStream());
			long start = sdf.parse(startDateTime).getTime() / 1000-600;
			long end = sdf.parse(endDateTime).getTime() / 1000+600;
			int count = 0;
			int filecount=0;
			for (File logFile : logDir.listFiles()) {
				String fileName = logFile.getName();
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
				filecount++;
				System.out.println("开始处理日志文件" + logFile.getName());
				BufferedReader reader = new BufferedReader(new FileReader(logFile));
				String log;
				while ((log = reader.readLine()) != null) {
					String[] fields = log.split(String.valueOf((char) 0x7F));
					if(fields.length>23){
					String kpiutcsec=fields[23].replace("-", "").replace(":", "").replace(" ", "");
					if ("3".equals(fields[0]) && "20170222120000".compareTo(kpiutcsec) <= 0
							&& "20170222140000".compareTo(kpiutcsec) > 0) {
						count++;
						out.println(log);
					}
					}
				}
			}
			System.out.println("总文件数：" + filecount);
			System.out.println("日志总条数："+count);
			out.close();
			client.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
