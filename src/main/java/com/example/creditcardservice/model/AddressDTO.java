package com.example.creditcardservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO for Address
 * @author javadevopsmc06
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
	private String street;
	private String area;
	private String city;
	private String state;
	private Long pincode;
	private String type;
}
