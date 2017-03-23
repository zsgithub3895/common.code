package com.zs.tcp.senddata;

import com.zs.sqm.spark.redis.RedisClient;

public class RedisTest {
	private static RedisClient client = null;
	static {
		client = new RedisClient();
		try {
			client.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println(client.set("zs", "0.5"));
	}

}
