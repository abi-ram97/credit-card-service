package com.example.creditcardservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.creditcardservice.model.CreditCardRequestDTO;
import com.example.creditcardservice.model.ServiceResponse;
import com.example.creditcardservice.service.CreditCardService;


/**
 * Accepts the credit card requests 
 * @author javadevopsmc06
 * @version 0.0.1
 */
@RestController
@RequestMapping("/v1/creditCards")
public class CreditCardServiceRestController {
	
	private CreditCardService creditCardService;
	
	@Autowired
	public CreditCardServiceRestController(CreditCardService creditCardService) {
		super();
		this.creditCardService = creditCardService;
	}

	@PostMapping("/create")
	public ResponseEntity<String> createApplication(@RequestBody @Valid CreditCardRequestDTO requestDto) {
		return ResponseEntity.ok(creditCardService.processCreditCardApplication(requestDto));
	}
	
	@GetMapping(value ="/requests/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<Object> getApplicationsById(@PathVariable("requestId") String applicationId) {
		return creditCardService.getApplicationById(applicationId);
	}
	
	@PutMapping("/update/{requestId}")
	public ServiceResponse<String> updateApplication(@PathVariable("requestId") String requestId,
			@RequestParam("status") String status) {
		return creditCardService.updateRequestState(requestId, status);
	}

}
