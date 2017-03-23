package com.zs.tcp.senddata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FlautCause {
	public static void main(String[] args) throws IOException {
		playRequest();
	}
	private static void playRequest() throws IOException{
				try {
					File logDir = new File("D:\\lag221");
					if (!logDir.isDirectory())
						System.exit(1);
					int count = 0;
					int total = 0;
					int c1=0;
					int c2=0;
					int c3=0;
					int c4=0;
					for (File logFile : logDir.listFiles()) {
						BufferedReader reader = new BufferedReader(new FileReader(logFile));
						String log;
						while ((log = reader.readLine()) != null) {
							String[] fields = log.split(String.valueOf((char) 0x7F),-1);
							if(fields.length>10&& "23".equals(fields[5])){
								String kpiutcsec = fields[8];
								String expertid = fields[10];
										if("20170221030000".compareTo(kpiutcsec) <= 0 && "20170221040000".compareTo(kpiutcsec) > 0 ){
											total++;
											if("2".equals(expertid)||"5".equals(expertid)||"6".equals(expertid)||"8".equals(expertid)||"11".equals(expertid)){
									        	c1++;
									        	count++;
									        }
									        if("15".equals(expertid)){
									        	c2++;
									        	count++;
									        }
									        if("1".equals(expertid)||"9".equals(expertid)||"10".equals(expertid)||"12".equals(expertid)){
									        	c3++;
									        	count++;
									        }
									        if("3".equals(expertid)||"13".equals(expertid)||"14".equals(expertid)){
									        	c4++;
									        	count++;
									        }
										}
							}
								
								}
								
						}
					
					System.out.println("总共" + total);
					System.out.println("日志条数：" + count);
					System.out.println("c1：" + c1);//运营商网络问题
					System.out.println("c2：" + c2);//家庭网络问题 
					System.out.println("c3：" + c3);//CDN平台问题
					System.out.println("c4：" + c4);//终端问题 
				} catch (Exception ex) {
					ex.printStackTrace();
				}
	}
	
}
