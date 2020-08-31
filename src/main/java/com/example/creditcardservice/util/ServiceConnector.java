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
	
	/**
	 * Generic method for HTTP post request
	 * @param <T>
	 * @param url
	 * @param request
	 * @param responseType
	 * @return T
	 */
	public <T> T post(String url, Object request, Class<T> responseType) {
		return restTemplate.postForObject(url, request, responseType);
	}
	
	/**
	 * Generic method for HTTP get request
	 * @param <T>
	 * @param url
	 * @param responseType
	 * @return T
	 */
	public <T> T get(String url, Class<T> responseType) {
		return restTemplate.getForObject(url, responseType);
	}
}
