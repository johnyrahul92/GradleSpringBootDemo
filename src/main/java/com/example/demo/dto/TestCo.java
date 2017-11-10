package com.example.demo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="test_co")
public class TestCo {
	
	@Id
	@Column(name="cid")
	private int  cid;
	@Column(name="name")
	private String name;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	private TestBeanDo  testBeanDo;
	
	
	public TestBeanDo getTestBeanDo() {
		return testBeanDo;
	}
	public void setTestBeanDo(TestBeanDo testBeanDo) {
		this.testBeanDo = testBeanDo;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
