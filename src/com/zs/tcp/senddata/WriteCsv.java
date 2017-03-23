package com.zs.tcp.senddata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;

public class WriteCsv {
	private static int count = 0;
	public static void main(String[] args) {
		try {
			writeFileCsv();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public  void writeF() throws IOException{
		try {
			FileOutputStream fis1 = new FileOutputStream("D:\\zs.txt");
			BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(fis1));
			FileOutputStream fis = new FileOutputStream("D:\\zs2.txt");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fis));
			for (int i = 0; i < 1; i++) {
				char c = 0x7f;
				String s0 = "3" + c + "455886829" + c + "20161209145809" + c
						+ "/030000001000/CCTV-11/c001_1481266487_1481266497.ts" + c + "173992" + c + "18" + c + "7" + c
						+ "4" + c + "53" + c + "2.1.7.12_M3" + c + "4" + c
						+ "http://111.11.121.183:6610/030000001000/CCTV-11/c001_1481266487_1481266497.ts?ispcode=9&IASHttpSessionId=SLB24065201612090602041570686&ts_min=1&srcurl=aHR0cDovLzExMS4xMS4xMjAuMTA4L2dpdHZfbGl2ZS9DQ1RWLTExL2MwMDFfMTQ4MTI2NjQ4N18xNDgxMjY2NDk3LnRz"
						+ c + "10" + c + "0" + c + "0" + c + "" + c + "177100" + c + "2432100" + c + "0" + c
						+ "111.11.121.183" + c + "192.168.0.101" + c + "2489872" + c + "37" + c + "20161209145903" + c
						+ "1" + c + "0" + c + "0" + c + "206" + c + "11670" + c + "0" + c + "0" + c + "41040857" + c
						+ "12330" + c + "0" + c + "0" + c + "15" + c + "0" + c + "0" + c + "0" + c + "-1" + c + "0" + c
						+ "0" + c + "0" + c + "0" + c + "1" + c + "0" + c + "0" + c + "0" + c + "0" + c + "0" + c + "0"
						+ c + "0" + c + "" + c + "";
				String s1 = "33";
				bw1.append(s0);
				//bw1.newLine();
				bw.append(s1);
				bw.newLine();
				count++;
					}
			bw1.flush();
			bw.flush();
			System.out.println("+++++" + count);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void writeFile() throws IOException{
		FileOutputStream fis = new FileOutputStream("D:\\zs.txt");
	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fis));
		bw.append("ss");
		bw.newLine();
		bw.flush();
	}
	
	
	public static void writeFileCsv() throws IOException{
		HashSet probeIdSet=new HashSet();
		HashMap<String,String> p4034=new HashMap<String,String>();
		File inFile =new File("D:\\QQMiniDL\\20170222\\data\\EVQMWorkingDir\\CacheRa");
		//File outFile =new File("D:\\state.csv");
		File outFile =new File("D:\\playrequest4034.csv");
		//File outFile =new File("D:\\lag.csv");
		BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
		File[] files = inFile.listFiles();
		int count=0;
		for(File f:files){
			   if(f.exists() && f.getName().endsWith(".csv")){
				   //System.out.println(f.getAbsolutePath());
				   BufferedReader br = new BufferedReader(new FileReader(f));
				   String inString="";
				   while((inString=br.readLine()) != null){
					   char c = 0x7f;
					   String[] s= inString.split(String.valueOf(c));
					   //String kpiutcsec= s[23];
					   //try{
						   //int logtype = Integer.valueOf(s[0]);
					 /*  }catch(Exception e){
						   System.out.println(inString);
						   e.printStackTrace();
					   }*/
					   
				      
				    	/*if(2 == logtype && !"0".equals(s[3])){
				    		bw.write(inString);
				    		bw.newLine();
				    	}*/
					   
					   if(s.length>40){
						   if("3".equals(s[0]) && "23".equals(s[7])){
							   String kpiutcsec= s[23];
							   String probeID= s[4];
							   String hasID= s[1];
							   String freezeTime= s[14];
							   String downSeconds= s[24];
							   if(kpiutcsec.compareTo("20170222000000")>=0 && kpiutcsec.compareTo("20170223000000")<0){
								   /*if("4034".equals(probeID)){
									   bw.write(hasID+"|"+kpiutcsec+"|"+freezeTime+"|"+downSeconds);
									   bw.newLine();
								   }*/
								   if("425053944".equals(hasID)){
								   bw.write(hasID+"|"+kpiutcsec+"|"+freezeTime+"|"+downSeconds);
								   bw.newLine();
							         }
								   //count++; 
									  //probeIdSet.add(probeID);
								   
									// bw.write(inString);
							    	// bw.newLine();
								   /*bw.write(inString);
						    	   bw.newLine(); */
							   }
				    	}
					   }
				    	/*if(4 == logtype ){
				    		bw.write(inString);
				    		bw.newLine();
				    	}*/
				    }
				   br.close();
			   }
			  
		}
		//System.out.println("+++"+probeIdSet.size());
	    bw.close();
	}
}