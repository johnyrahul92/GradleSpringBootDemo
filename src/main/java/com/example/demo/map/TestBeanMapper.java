package com.example.demo.map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.dto.TestBeanDo;
import com.example.demo.pojo.TestBeanResponse;

@Mapper(componentModel="spring")
public interface TestBeanMapper {
	
	TestBeanMapper INSTANCE = Mappers.getMapper(TestBeanMapper.class);
	
	TestBeanResponse dtoToPojo(TestBeanDo testBeanDo);
	TestBeanDo pojoToDto(TestBeanResponse testBeanResponse);

}
