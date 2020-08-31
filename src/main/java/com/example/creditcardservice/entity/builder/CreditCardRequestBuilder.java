package com.example.creditcardservice.entity.builder;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.creditcardservice.entity.Address;
import com.example.creditcardservice.entity.CreditCardRequest;
import com.example.creditcardservice.model.CreditCardRequestDTO;


/**
 * Builder for CreditCardRequest
 * @author javadevopsmc06
 *
 */
@Component
public class CreditCardRequestBuilder {
	private ModelMapper modelMapper;
	
	@Autowired
	public CreditCardRequestBuilder(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	/**
	 * builds creditcard request entity
	 * @param requestDto
	 * @return CreditCardRequest
	 */
	public  CreditCardRequest buildRequest(CreditCardRequestDTO requestDto) {
		CreditCardRequest request = modelMapper.map(requestDto, CreditCardRequest.class);
		request.setStatus("RECEIVED");
		requestDto.getAddress().forEach(address ->request.addAddress(modelMapper.map(address, Address.class)));
		return request;
	}
}
