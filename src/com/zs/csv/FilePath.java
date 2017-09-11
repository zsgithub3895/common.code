package com.zs.csv;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JTextField;


public class FilePath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFileChooser jf=new JFileChooser("c:");
		jf.setDialogTitle("导出并保�?");
		int value=jf.showSaveDialog(null);		
		if(value==JFileChooser.APPROVE_OPTION){    //判断窗口是否点的是打�?或保�?	   
		      File getPath=jf.getSelectedFile();       //取得路径
		      Object[] head = {"客户姓名", "证件类型", "证件号码", "银行账号" };
		        List<Object> headList = Arrays.asList(head);
		        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		        List<List<Object>>  dataList=new ArrayList<List<Object>>();
		        List<Object>  list1=new ArrayList<Object>();
		        TerminalEntity tn1=new TerminalEntity();
		        tn1.setName("张三");
		        tn1.setType("身份�?");
		        tn1.setCode("3412221988110151413");
		        tn1.setCountcode("10000");
		        list1.add(tn1);
		        dataList.add(list1);
		        
		        List<Object>  list2=new ArrayList<Object>();
		        TerminalEntity tn2=new TerminalEntity();
		        tn2.setName("张三2");
		        tn2.setType("身份�?2");
		        tn2.setCode("34122219881101514132");
		        tn2.setCountcode("100002");
		        list2.add(tn2);
		        dataList.add(list2);
				String datetimeStr =  sf.format(new Date());
				String fileName = "客户列表_" + datetimeStr;
				CSVUtils.createCSVFile(headList, dataList, getPath.toString(), fileName);
		        //用流在将你的数据输出
		   }
	}

}
