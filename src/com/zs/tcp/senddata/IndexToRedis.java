package com.zs.tcp.senddata;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.zs.sqm.spark.redis.RedisClient;


public class IndexToRedis {
	private static Logger logger = Logger.getLogger(IndexToRedis.class);
	static DecimalFormat df = new DecimalFormat("0.0000");
	private static RedisClient client = null;

	static {
		client = new RedisClient();
		try {
			client.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void toRedis(HashMap<String, Double> hm) {
		for (Entry<String, Double> en : hm.entrySet()) {
			if (en != null && en.getValue() != null) {
				client.setObject(en.getKey(), Double.parseDouble(df.format(en.getValue())));
			}
		}
	}

	public static String getBaseInfo(String stb) {
		String baseIbfo = null;
		try {
			baseIbfo = client.get(stb);
		} catch (Exception e) {
			logger.error("从redis获取数据失败", e);
		}
		return baseIbfo;
	}
	
}
