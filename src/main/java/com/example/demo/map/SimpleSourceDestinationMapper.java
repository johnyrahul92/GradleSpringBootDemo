package com.example.demo.map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface SimpleSourceDestinationMapper {
	
	SimpleSourceDestinationMapper  INSTANCE= Mappers.getMapper( SimpleSourceDestinationMapper.class );
	
	SimpleDestination sourceToDestination(SimpleSource source);

	SimpleSource destinationToSource(SimpleDestination destination);
}
