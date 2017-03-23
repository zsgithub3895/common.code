package com.zs.tcp.senddata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HttpPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletInputStream sis = null;
	    String xmlData = "";
	    /*Enumeration ss=req.getHeaders("connection");
	   while(ss.hasMoreElements()){
	    	System.out.println("++"+ss.nextElement());
	    }*/
	    
	    System.out.println(req.getCharacterEncoding());
		try {  
	            // 取HTTP请求流  
	            sis = req.getInputStream();  
	            BufferedReader br = new BufferedReader(new InputStreamReader(sis,"UTF-8"));
	            String buffer = null;
	            StringBuffer sb = new StringBuffer();
	            while ((buffer = br.readLine()) != null) {
	                   sb.append(buffer);
	                }
	            xmlData = sb.toString();
		//System.out.println("+++++xml="+xmlData);
	} catch (IOException e) {  
	    e.printStackTrace();  
	}
	}
}
