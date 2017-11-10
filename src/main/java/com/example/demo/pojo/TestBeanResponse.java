package com.example.demo.pojo;

import java.util.Date;
import java.util.Set;

public class TestBeanResponse {

	private int id;
	private String name;
	private String description;
	private Date updatedDate;

	private Set<TestCoResponse> testCos;

	

	public Set<TestCoResponse> getTestCos() {
		return testCos;
	}

	public void setTestCos(Set<TestCoResponse> testCos) {
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
