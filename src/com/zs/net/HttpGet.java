package com.zs.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JFileChooser;

    /** 
    * 从网络Url中下载文件 
    * @param urlStr 
    * @param fileName 
    * @param savePath 
    * @throws IOException 
    */ 
public class HttpGet{
   public static void  downLoadFromUrl(String urlStr,String fileName) throws IOException{
	   JFileChooser jf = new JFileChooser();  
	   jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);  
	   jf.showDialog(null,null);
	   File fi = jf.getSelectedFile();
	   String savePath = fi.getAbsolutePath();
	   URL url = new URL(urlStr);    
       HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
               //设置超时间为3秒  
       conn.setConnectTimeout(3*1000);  
       //防止屏蔽程序抓取而返回403错误  
       conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
 
       //得到输入流  
       InputStream inputStream = conn.getInputStream();    
       //获取自己数组  
       byte[] getData = readInputStream(inputStream);      
 
       //文件保存位置  
       File saveDir = new File(savePath);
       if(!saveDir.exists()){  
           saveDir.mkdir();  
       }  
       File file = new File(saveDir+File.separator+fileName);     
       FileOutputStream fos = new FileOutputStream(file);       
       fos.write(getData);   
       if(fos!=null){  
           fos.close();    
       }  
       if(inputStream!=null){  
           inputStream.close();  
       }  
       System.out.println(fileName+" download success");   
   }  
 
   /** 
    * 从输入流中获取字节数组 
    * @param inputStream 
    * @return 
    * @throws IOException 
    */  
   public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
       byte[] buffer = new byte[1024];    
       int len = 0;    
       ByteArrayOutputStream bos = new ByteArrayOutputStream();    
       while((len = inputStream.read(buffer)) != -1) {    
           bos.write(buffer, 0, len);    
       }    
       bos.close();    
       return bos.toByteArray();    
   }    
 
   public static void main(String[] args) {  
       try{  
           downLoadFromUrl("http://dcs.sunniwell.net:8000/HFS/download/logcat-009901FF00011810171060313B0F9191-20171229153201.txt",  
                   "logcat-009901FF00011810171060313B0F9191-20171229153201.txt");  
       }catch (Exception e) {  
       }  
   } 
   
}

