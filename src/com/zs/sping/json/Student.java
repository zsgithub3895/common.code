package com.zs.sping.json;

import com.alibaba.fastjson.annotation.JSONField;

public class Student {
	private int S_ID;
	private String name;
	private int age;
	private String[] hobby;

	@JSONField(name="S_ID")
	public int getS_ID() {
		return S_ID;
	}

	public void setS_ID(int s_ID) {
		S_ID = s_ID;
	}

	public String[] getHobby() {
		return hobby;
	}

	public void setHobby(String[] hobby) {
		this.hobby = hobby;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
