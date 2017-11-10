package com.example.demo.map;

public class MainDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SimpleSource simpleSource = new SimpleSource();
		
		simpleSource.setName("Rahul");
		simpleSource.setDescription("Hai how  are you");
		
		SimpleDestination destination= SimpleSourceDestinationMapper.INSTANCE.sourceToDestination(simpleSource);
		
		System.out.println(destination.getName());

	}

}
