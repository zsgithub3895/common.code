package com.zs.sping.json;

import com.alibaba.fastjson.annotation.JSONField;

public class Person {
	private int ID;
	private Student student;
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@JSONField(name="ID")
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
