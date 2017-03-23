package com.zs.tcp.senddata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class StartAnalysis {
	static Set<String> probeIdSet = new HashSet<String>();
	static Map<String, String> startMap = new HashMap<String, String>();
	static ArrayList<String> probeids = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		startUser();
	}

	private static void startUser() throws IOException {
		try {
			File inFile = new File("D:\\state219\\probeid.csv");
			BufferedReader br = new BufferedReader(new FileReader(inFile));
			String inString = null;
			while ((inString = br.readLine()) != null) {
				probeIdSet.add(inString);
			}
			
			File in = new File("D:\\state219\\state.csv");
			BufferedReader brState = new BufferedReader(new FileReader(in));
			int count = 0;
			int userCount = 0;
			String log;
			while ((log = brState.readLine()) != null) {
				String[] fields = log.split(String.valueOf((char) 0x7F), -1);
				String probeid = fields[1];
				String hasid = fields[2];
				String kpiutcsec = fields[4];
				if ("20170219030500".compareTo(kpiutcsec) <= 0 && "20170219040000".compareTo(kpiutcsec) >= 0) {
					startMap.put(probeid, probeid + "|" + hasid);
					count++;
				}
			}
			
			for (String key : startMap.keySet()) {
				if (probeIdSet.contains(key)) {
					userCount++;
				}
			}

			System.out.println("日志条数：" + count);
			System.out.println("开机用户数" + userCount);
			startMap.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}