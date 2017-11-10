package com.example.demo.map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.dto.TestCo;
import com.example.demo.pojo.TestCoResponse;

@Mapper(componentModel="spring")
public interface TestCoMapper {
	
	TestCoMapper INSTANCE =Mappers.getMapper(TestCoMapper.class);
	

	TestCoResponse dtoToPojo(TestCo testCo);
	TestCo pojoToDto(TestCoResponse testCoResponse);

}
