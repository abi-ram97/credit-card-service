package com.example.creditcardservice.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.creditcardservice.entity.CreditCardRequest;
import com.example.creditcardservice.model.CreditCardRequestDTO;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * Configuration for Credit Card Service
 * @author javadevopsmc06
 *
 */
@Configuration
public class CreditCardServiceConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.example.creditcardservice.controller"))
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.createTypeMap(CreditCardRequestDTO.class, CreditCardRequest.class)
			.addMappings(modelMapper->modelMapper.skip(CreditCardRequest::setAddress));
		
		return mapper;
	}
	

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
