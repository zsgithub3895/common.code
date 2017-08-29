package com.zs.sping.json;

import javax.servlet.ServletInputStream;

import org.json.JSONArray;
import org.json.JSONObject;

public class StringToJSON {

	public static void main(String[] args) {
		ServletInputStream sis = null;
		String xmlData1 = "{\"ID\":"+1000+",\"student\":"+"{\"S_ID\":"+108320199+",\"age\":"+20+",\"hobby\":[\"唱歌\",\"跳舞\"],"+"\"name\":\"xiaming\"}}";
		String xmlData = "[{\"ID\":"+1000+",\"student\":"+"{\"S_ID\":"+108320199+",\"age\":"+20+",\"hobby\":[\"唱歌\",\"跳舞\"],"+"\"name\":\"xiaming\"}}]";
		try {
			// 将字符串转换成jsonObject对象
			  JSONObject object = new JSONObject(xmlData1);
			  int p_one =  (int) object.get("ID");
			  System.out.println(p_one);
			
			// 将字符串数组转换成jsonObject对象
			JSONArray myJsonArray = new JSONArray(xmlData);
			for (int i = 0; i < myJsonArray.length(); i++) {
				// 获取每一个JsonObject对象
				JSONObject myjObject = myJsonArray.getJSONObject(i);
				int per =  (int) myjObject.get("ID");
				System.out.println(per);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		

	}

}
