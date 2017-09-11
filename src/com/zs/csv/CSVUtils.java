package com.zsgithub.hello.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CSVUtils {
	
	public static void main(String[] args){
		// 设置表格头
        Object[] head = {"客户姓名", "证件类型", "证件号码", "银行账号" };
        List<Object> headList = Arrays.asList(head);
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
        List<List<Object>>  dataList=new ArrayList<List<Object>>();
        List<Object>  list1=new ArrayList<Object>();
        TerminalEntity tn1=new TerminalEntity();
        tn1.setName("张三");
        tn1.setType("身份证");
        tn1.setCode("3412221988110151413");
        tn1.setCountcode("10000");
        list1.add(tn1);
        dataList.add(list1);
        
        List<Object>  list2=new ArrayList<Object>();
        TerminalEntity tn2=new TerminalEntity();
        tn2.setName("张三2");
        tn2.setType("身份证2");
        tn2.setCode("34122219881101514132");
        tn2.setCountcode("100002");
        list2.add(tn2);
        dataList.add(list2);
		String datetimeStr =  sf.format(new Date());
		String fileName = "客户列表_" + datetimeStr;
		String downloadFilePath = "C:" + File.separator + "cap4j" + File.separator + "download" + File.separator;
		 // 导出CSV文件
         createCSVFile(headList, dataList, downloadFilePath, fileName);
	}
	
	 /**
     * CSV文件生成方法
     * @param head
     * @param dataList
     * @param outPutPath
     * @param filename
     * @return
     */
    public static void createCSVFile(List<Object> head, List<List<Object>> dataList,String outPutPath, String filename) {
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
            // 写入文件头部
            writeRow(head, csvWtriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow1(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写一行数据方法
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }
    private static void writeRow1(List<Object> row, BufferedWriter csvWriter) throws IOException {
    	// 写入文件头部
    	for (Object data : row) {		
    		TerminalEntity terminalEntity=(TerminalEntity)data;
    		StringBuffer sb = new StringBuffer();
    	    sb.append("\"").append(terminalEntity.getName()).append("\",");
    	    sb.append("\"").append(terminalEntity.getType()).append("\",");
    	    sb.append("\"").append(terminalEntity.getCode()).append("\",");
    	    sb.append("\"").append(terminalEntity.getCountcode()).append("\"");
    	    String rowStr=sb.toString();
    		csvWriter.write(rowStr);
    	}
    	csvWriter.newLine();
    }

    
    
}
