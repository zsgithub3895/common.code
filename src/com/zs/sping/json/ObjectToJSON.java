package com.zs.sping.json;

import com.alibaba.fastjson.JSON;

/**
 * 获得对应json字段，只需在get方法上加JSONField*/
public class ObjectToJSON {

	public static void main(String[] args) {
            Person p = new Person();
            p.setID(1000);
            Student st = new Student();
            st.setName("xiaming");
            st.setAge(20);
            st.setS_ID(108320199);
            String[] hobby = {"唱歌","跳舞"};
            st.setHobby(hobby);
            p.setStudent(st);
            String json = JSON.toJSONString(p);
            System.out.println(json);
	}

}
