package com.example.demo;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author TCS This filter is a utility to log the HTTPRequest and HTTP Response
 *         Payload
 */
@Component
public class PayloadLoggingFilter extends PayloadFilter {

	/** The logger used for Instrumentation and Auditing purposes. */
	private static final Logger LOG = LogManager.getLogger(PayloadLoggingFilter.class);

	@Override
	protected void logTrace(Map<String, Object> trace) {
		//LOG.info(trace);
		//System.out.println(trace);
	}

}
