package com.example.creditcardservice.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.creditcardservice.entity.CreditCardRequest;
import com.example.creditcardservice.entity.builder.CreditCardRequestBuilder;
import com.example.creditcardservice.exception.CreditCardServiceException;
import com.example.creditcardservice.model.ApprovalDTO;
import com.example.creditcardservice.model.CreditCardRequestDTO;
import com.example.creditcardservice.model.ServiceResponse;
import com.example.creditcardservice.repository.CreditCardRequestRepository;
import com.example.creditcardservice.service.CreditCardService;
import com.example.creditcardservice.util.ServiceConnector;

import lombok.extern.slf4j.Slf4j;

/**
 * To process the creditcard requests
 * @author javadevopsmc06
 *
 */
@Service
@Slf4j
public class CreditCardServiceImpl implements CreditCardService{
	
	private static final String ERROR_MSG="Unable to process the request. Please try again later";
	
	@Value("${service.approval}")
	private String approvalServiceUrl;
	
	private CreditCardRequestRepository creditCardRepository;
	
	private ModelMapper modelMapper;
	
	private CreditCardRequestBuilder requestBuilder;
	
	private ServiceConnector serviceConnector;
	
	@Autowired
	public CreditCardServiceImpl(CreditCardRequestRepository creditCardRepository, ModelMapper modelMapper,
			CreditCardRequestBuilder requestBuilder, ServiceConnector serviceConnector) {
		super();
		this.creditCardRepository = creditCardRepository;
		this.modelMapper = modelMapper;
		this.requestBuilder = requestBuilder;
		this.serviceConnector = serviceConnector;
	}

	/**
	 * generates new creditcard request
	 * @param CreditCardRequestDto
	 * @return requestId
	 */
	@Override
	public String processCreditCardApplication(CreditCardRequestDTO requestDto) {
		log.info("Adding request for customer [{}]", requestDto.getCustomerId());
		try {
		CreditCardRequest request = requestBuilder.buildRequest(requestDto);
		creditCardRepository.save(request);
		sendForApproval(request);
		return request.getRequestId();
		} catch(CreditCardServiceException ccse) {
			log.error(ccse.getMessage(), ccse);
			return ERROR_MSG;
		}
	}
	
	
	private void sendForApproval(CreditCardRequest request) {
		try {
		ApprovalDTO approval = ApprovalDTO.builder().customerId(request.getCustomerId())
				.requestId(request.getRequestId())
				.status("RECEIVED")
				.build();
		log.info("Sending for approval {}", approval);
		String approvalId = serviceConnector.post(approvalServiceUrl+"createApproval", approval, String.class);
		request.setApprovalId(approvalId);
		creditCardRepository.save(request);
		} catch(Exception e) {
			log.error("Error while sending approval for [{}]", request.getRequestId());
			throw new CreditCardServiceException(e.getMessage());
		}
	}

	/**
	 * To find request by requestId
	 * @param applicationId/requestId
	 * @return CreditCardRequest
	 */
	@Override
	public ServiceResponse<Object> getApplicationById(String applicationId) {
		log.info("Getting request details for id [{}]", applicationId);
		Optional<CreditCardRequest> optionalRequest = creditCardRepository.findById(applicationId);
		if(optionalRequest.isPresent()) {
			CreditCardRequestDTO requestDto = modelMapper.map(optionalRequest.get(), CreditCardRequestDTO.class);
			return buildServiceResponse(requestDto, HttpStatus.OK);
		}
		return buildServiceResponse("No Details found for "+applicationId, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Updates the status of the request
	 * @param requestId
	 * @param status
	 * @return message
	 */
	@Override
	public ServiceResponse<String> updateRequestState(String requestId, String status) {
		log.info("Updating status for [{}]", requestId);
		Optional<CreditCardRequest> optionalRequest = creditCardRepository.findById(requestId);
		if(optionalRequest.isPresent()) {
			CreditCardRequest request = optionalRequest.get();
			request.setStatus(status);
			creditCardRepository.save(request);
			return buildServiceResponse("Updated successfully", HttpStatus.OK);
		}
		return buildServiceResponse("No Details found for "+requestId, HttpStatus.NOT_FOUND);
	}
	
	private <T>ServiceResponse<T> buildServiceResponse(T body, HttpStatus status){
		 return new ServiceResponse<>(body, status.value());
	}

}
