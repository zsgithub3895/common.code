package com.zs.tcp.senddata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class FaultUserDistribution {
	static DecimalFormat nf = new DecimalFormat("0.00");
	public static void main(String[] args) {
		try {
			runCause();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static void runCause() throws IOException {
		long start = System.currentTimeMillis();
			boolean flag = true;
			Random r = new Random();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			File inFile =new File("D:\\playrequest.csv");
			File outFile =new File("D:\\playrequest20170222.csv");
			//File outFile4034 =new File("D:\\4034sqm.csv");
			//BufferedWriter bw4034 = new BufferedWriter(new FileWriter(outFile4034));
			BufferedWriter bwResult = new BufferedWriter(new FileWriter(outFile));
			BufferedReader br = new BufferedReader(new FileReader(inFile));
			Map<String,String> hasIDMap = new HashMap<String,String>();
			Map<String,String> probeIDMap =new HashMap<String,String>();
			int count=0;
			int total=0;
			String inString="";
			
			while((inString=br.readLine()) !=  null){
				char c = 0x7F;
				String[] ex = inString.split(String.valueOf(c),-1);
				String hasId = ex[1];
				String probeID = ex[4];
				String provinceID = ex[7];
				String hasType = ex[10];
				//String playSecond = ex[12];
				String freezTime= ex[14];
				String kpiutcsec = ex[23];
				String downSeconds = ex[24];
				String value = kpiutcsec+"|"+probeID+"|"+freezTime+"|"+downSeconds;
					//if("20170221030000".compareTo(kpiutcsec) <= 0 && "20170221040000".compareTo(kpiutcsec) > 0){
						  String vv = hasIDMap.get(hasId);
				        	 if(null != vv && hasIDMap.containsKey(hasId)){
				        		 String kpiutcsecTwo=vv.split("\\|")[0];
				        		 if(kpiutcsec.compareTo(kpiutcsecTwo) > 0){
							    		hasIDMap.put(hasId, value);
						    	   }
				        	 }else{
				        		 hasIDMap.put(hasId, value);
				        	 }
				        	// count++;
						//}
					
				}
			System.out.println("+过滤后+"+hasIDMap.size());
				
			for(Entry<String, String> en:hasIDMap.entrySet()){
				String kpiutcsecTwo=en.getValue().split("\\|")[0];
				String probeIDTwo = en.getValue().split("\\|")[1];
				long freezTimeTwo =Long.valueOf(en.getValue().split("\\|")[2]);
				long downSecondsTwo =Long.valueOf(en.getValue().split("\\|")[3]);
				if("4034".equals(probeIDTwo)){
					 System.out.println(en.getKey()+"|"+en.getValue());
				   }
				String v = probeIDMap.get(probeIDTwo);
	        	 if(null != v && probeIDMap.containsKey(probeIDTwo)){
	        		 String time=v.split("\\|")[0];
	        		 long vfreeze =Long.valueOf(v.split("\\|")[1]);
	 				 long vdown =Long.valueOf(v.split("\\|")[2]);
	 				freezTimeTwo+=vfreeze;
	 				downSecondsTwo+=vdown;
	 				probeIDMap.put(probeIDTwo,time+"|"+freezTimeTwo+"|"+downSecondsTwo);
	        	 }else{
	        		 probeIDMap.put(probeIDTwo,kpiutcsecTwo+"|"+freezTimeTwo+"|"+downSecondsTwo);
	        	 }
			}
			
			
			int t1=0;
			int t2=0;
			int t3=0;
			int t4=0;
			
			System.out.println("++++"+probeIDMap.size());
			for(Entry<String, String> enen:probeIDMap.entrySet()){
				/*if("4034".equals(enen.getKey())){
					 System.out.println("====="+enen.getKey()+"|"+enen.getValue());
				   }*/
				String kpiutcsecThree= enen.getValue().split("\\|")[0];
				long r1= Long.valueOf(enen.getValue().split("\\|")[1]);
				long r2= Long.valueOf(enen.getValue().split("\\|")[2]);
				double result = (r1*1.0d)/r2;
				/*if(result > 50000){
					t1++;//严重
					total++;
				}*/
				if(result > 0 && result<=10000){
					bwResult.write("偶尔用户故障,"+enen.getKey()+",23,"+r1+","+r2+","+nf.format(result/10000)+"%");
					bwResult.newLine();
					t3 ++;//偶尔
					total++;
				}
				if(result>10000 && result<=50000){
					bwResult.write("较多用户故障,"+enen.getKey()+",23,"+r1+","+r2+","+nf.format(result/10000)+"%");
					bwResult.newLine();
					t2 ++;//较多
					total++;
				}
			
				
				/*if(result == 0){
					t4 ++;//无故障
					total++;
				}*/
				
			}
				//out.println(inString);
			//bwResult.write(t1+","+t2+","+t3+","+t4);
			System.out.println("total="+total);
			//System.out.println(t1);
			System.out.println(t3);
			System.out.println(t2);
			//System.out.println(t4);
    }
}