package com.example.creditcardservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.creditcardservice.entity.CreditCardRequest;
import com.example.creditcardservice.entity.builder.CreditCardRequestBuilder;
import com.example.creditcardservice.model.CreditCardRequestDTO;
import com.example.creditcardservice.model.ServiceResponse;
import com.example.creditcardservice.repository.CreditCardRequestRepository;
import com.example.creditcardservice.service.impl.CreditCardServiceImpl;
import com.example.creditcardservice.util.ServiceConnector;

@ExtendWith(SpringExtension.class)
@SuppressWarnings("rawtypes")
class CreditCardServiceImplTest {
	
	@InjectMocks
	private CreditCardServiceImpl service;
	
	@Mock
	private CreditCardRequestRepository creditCardRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private CreditCardRequestBuilder requestBuilder;
	
	@Mock
	private ServiceConnector serviceConnector;
	
	private CreditCardRequestDTO requestDto;
	private CreditCardRequest request;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FieldUtils.writeField(service, "approvalServiceUrl", "http://localhost:8082", true);
		
		requestDto = new CreditCardRequestDTO();
		request = new CreditCardRequest();
		request.setRequestId("request1");
	}

	@Test
	void testProcessCreditCardApplication() {
		when(requestBuilder.buildRequest(Mockito.any(CreditCardRequestDTO.class))).thenReturn(request);
		when(creditCardRepository.save(Mockito.any(CreditCardRequest.class))).thenReturn(request);
		when(serviceConnector.post(Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn("Updated");
		String result = service.processCreditCardApplication(requestDto);
		assertNotNull(result, "RequestId is null");
		assertEquals("request1", result, "Request Id not matched");
		verify(creditCardRepository, times(2)).save(Mockito.any(CreditCardRequest.class));
		verify(requestBuilder, times(1)).buildRequest(Mockito.any(CreditCardRequestDTO.class));
		verify(serviceConnector, times(1)).post(Mockito.anyString(), Mockito.any(), Mockito.any());
	}

	@Test
	void testGetApplicationById() {
		when(creditCardRepository.findById(Mockito.anyString())).thenReturn(Optional.of(request));
		when(modelMapper.map(Mockito.any(CreditCardRequest.class),Mockito.eq(CreditCardRequestDTO.class))).thenReturn(requestDto);
		ServiceResponse response = service.getApplicationById("request1");
		assertNotNull(response.getBody(), "ResponseBody is null");
		assertEquals(requestDto, response.getBody(), "Response body not matched");
		assertEquals(200, response.getStatus(), "Response status not matched");
		verify(creditCardRepository, times(1)).findById(Mockito.anyString());
	}
	
	@Test
	void testGetApplicationByIdNotFound() {
		when(creditCardRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		ServiceResponse response = service.getApplicationById("request1");
		assertNotNull(response.getBody(), "ResponseBody is null");
		assertEquals(404, response.getStatus(), "Response status not matched");
		assertEquals("No Details found for request1", response.getBody(), "Response body not matched");
		verify(creditCardRepository, times(1)).findById(Mockito.anyString());
	}

	@Test
	void testUpdateRequestState() {
		when(creditCardRepository.findById(Mockito.anyString())).thenReturn(Optional.of(request));
		when(creditCardRepository.save(Mockito.any(CreditCardRequest.class))).thenReturn(request);
		String result = service.updateRequestState("request1", "ABORTED").getBody();
		assertNotNull(result, "Response is null");
		assertEquals("Updated successfully", result, "Response not matched");
		verify(creditCardRepository, times(1)).findById(Mockito.anyString());
		verify(creditCardRepository, times(1)).save(Mockito.any(CreditCardRequest.class));
	}
	
	@Test
	void testUpdateRequestStateNotFound() {
		when(creditCardRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		String result = service.updateRequestState("request1", "ABORTED").getBody();
		assertNotNull(result, "Response is null");
		assertEquals("No Details found for request1", result, "Response not matched");
		verify(creditCardRepository, times(1)).findById(Mockito.anyString());
	}

}
