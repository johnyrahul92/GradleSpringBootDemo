package com.example.demo.Dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.OlbUserDetailDo;
import com.example.demo.dto.StmtDocument;
import com.example.demo.dto.StudentDo;
import com.example.demo.pojo.MonthlyStatementResponse;
import com.example.demo.pojo.TestBeanResponse;

public interface TestDao {
	
	String getById(int id);
	
	void saveStudent(StudentDo studentDo);

	TestBeanResponse getByName(String name);
	
	String saveFile(MultipartFile file, String name,String id);
	
	StmtDocument getPDF(String id);
	
	OlbUserDetailDo getUserDetail(String userId);

	List<MonthlyStatementResponse> getLastSix(String cardNo);

}
