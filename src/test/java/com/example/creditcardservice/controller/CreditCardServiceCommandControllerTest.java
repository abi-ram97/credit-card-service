package com.example.creditcardservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.creditcardservice.model.CreditCardRequestDTO;
import com.example.creditcardservice.model.ServiceResponse;
import com.example.creditcardservice.service.CreditCardService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SuppressWarnings({"rawtypes", "unchecked"})
class CreditCardServiceCommandControllerTest {
	
	private MockMvc mockMvc;
	
	private ObjectMapper mapper;
	
	@Mock
	private CreditCardService service;
	
	private CreditCardRequestDTO requestDto;
	private ServiceResponse response;

	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new CreditCardServiceRestController(service)).build();
		mapper = new ObjectMapper();
		
		requestDto = new CreditCardRequestDTO();
		requestDto.setAddress(Collections.singleton(null));
		requestDto.setMobileNumber("1234567890");
		requestDto.setAnnualIncome(1000.00);
		requestDto.setPanCard("PAN1234567");
	}

	@Test
	void testCreateApplication() throws Exception {
		requestDto.setCustomerId("customer1");
		requestDto.setEmailId("test@test.com");
		when(service.processCreditCardApplication(Mockito.any(CreditCardRequestDTO.class))).thenReturn("request1");
		MvcResult result = this.mockMvc.perform(post("/v1/creditCards/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestDto)))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(result.getResponse(), "Create request response is null");
		assertTrue(result.getResponse().getContentAsString().contains("request1"), "Reponse not matched");
		verify(service, times(1)).processCreditCardApplication(Mockito.any(CreditCardRequestDTO.class));
	}
	
	@Test
	void testCreateApplicationValidationError() throws Exception {
		this.mockMvc.perform(post("/v1/creditCards/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestDto)))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	void testGetApplicationsById() throws Exception {
		requestDto.setCustomerId("customer1");
		requestDto.setEmailId("test@test.com");
		response = ServiceResponse.builder().body(requestDto).status(200).build();
		when(service.getApplicationById(Mockito.anyString())).thenReturn(response);
		MvcResult result = this.mockMvc.perform(get("/v1/creditCards/requests/request1"))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(result.getResponse(), "Get request response is null");
		ServiceResponse<CreditCardRequestDTO> actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ServiceResponse.class);
		assertEquals(response.getStatus(), actualResponse.getStatus(), "Response status not matched");
	}
	
	@Test
	void testGetApplicationsByIdErrorResponse() throws Exception {
		response = ServiceResponse.builder().body("No details found").status(500).build();
		when(service.getApplicationById(Mockito.anyString())).thenReturn(response);
		MvcResult result = this.mockMvc.perform(get("/v1/creditCards/requests/request1"))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(result.getResponse(), "Get request response is null");
		ServiceResponse actualResponse = mapper.readValue(result.getResponse().getContentAsString(), ServiceResponse.class);
		assertEquals(response.getStatus(), actualResponse.getStatus(), "Response status not matched");
		assertEquals(response.getBody(), actualResponse.getBody(), "Response body not matched");
	}

	@Test
	void testUpdateApplication() throws Exception {
		ServiceResponse<String> response = new ServiceResponse<>("Updated", 200);
		when(service.updateRequestState(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		MvcResult result = this.mockMvc.perform(put("/v1/creditCards/update/request1")
				.queryParam("status", "ABORTED"))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(result.getResponse(), "Update response is null");
	}

}
