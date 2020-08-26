package com.example.creditcardservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.creditcardservice.controller.CreditCardServiceRestController;
import com.example.creditcardservice.service.CreditCardService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CreditCardServiceApplicationTests {
	
	@Autowired
	private CreditCardServiceRestController controller;
	
	@Autowired
	private CreditCardService service;

	@Test
	void contextLoads() {
		assertNotNull(controller, "Controller is null");
		assertNotNull(service, "Service is null");
	}

}
