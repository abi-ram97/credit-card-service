package com.example.creditcardservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * Generic Connector between services
 * @author javadevopsmc06
 *
 */
@Component
public class ServiceConnector {
	private RestTemplate restTemplate;

	@Autowired
	public ServiceConnector(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public <T> T post(String url, Object request, Class<T> responseType) {
		return restTemplate.postForObject(url, request, responseType);
	}
	
	public <T> T get(String url, Class<T> responseType) {
		return restTemplate.getForObject(url, responseType);
	}
}
