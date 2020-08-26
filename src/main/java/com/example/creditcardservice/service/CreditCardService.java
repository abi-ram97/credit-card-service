package com.example.creditcardservice.service;

import com.example.creditcardservice.model.CreditCardRequestDTO;
import com.example.creditcardservice.model.ServiceResponse;

public interface CreditCardService {
	public String processCreditCardApplication(CreditCardRequestDTO requestDto);
	public ServiceResponse<Object> getApplicationById(String applicationId);
	public ServiceResponse<String> updateRequestState(String requestId, String status);
}
