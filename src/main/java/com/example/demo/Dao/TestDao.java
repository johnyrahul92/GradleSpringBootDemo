package com.example.demo.Dao;

import com.example.demo.dto.StudentDo;
import com.example.demo.dto.TestBeanDo;
import com.example.demo.pojo.TestBeanResponse;

public interface TestDao {
	
	String getById(int id);
	
	void saveStudent(StudentDo studentDo);

	TestBeanResponse getByName(String name);

}
