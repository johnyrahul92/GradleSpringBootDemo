package com.example.demo.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity(name="testbeando")
public class TestBeanDo {
	
	
	
	@Id
	@Column(name="id")
	private int  id;
	private String name;
	private String description;
	
	@Column(name="updateddate")
	private Date updatedDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "testBeanDo")
	private Set<TestCo> testCos;	
	
	public Set<TestCo> getTestCos() {
		return testCos;
	}
	public void setTestCos(Set<TestCo> testCos) {
		this.testCos = testCos;
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	

}
