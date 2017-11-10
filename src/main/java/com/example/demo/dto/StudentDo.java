package com.example.demo.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="STUDENT")
public class StudentDo {
	
	@Id
	private int  id;
	private String name;
	private String subject;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
		

}
