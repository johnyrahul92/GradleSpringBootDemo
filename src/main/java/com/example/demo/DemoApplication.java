package com.example.demo;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAutoConfiguration
@PropertySource(value = { "classpath:test.properties" })
public class DemoApplication {
	
	private static final Logger LOG = LogManager.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		LOG.info("_---------------Start----------------------");
		SpringApplication.run(DemoApplication.class, args);
		LOG.info("_---------------Started----------------------");
	}
}
