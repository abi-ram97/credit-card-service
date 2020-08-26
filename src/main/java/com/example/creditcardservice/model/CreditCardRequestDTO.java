package com.example.creditcardservice.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * DTO for CreditCardRequest
 * @author javadevopsmc06
 *
 */
@Data
public class CreditCardRequestDTO {
	@NotNull(message = "CustomerId must not be null")
	private String customerId;

	private String approvalId;

	private String fullName;

	@Email(message = "Incorrect email format")
	@NotNull(message = "EmailId must not be null")
	private String emailId;

	@Size(min = 10, max = 10, message = "Mobile Number must be of length 10")
	@NotNull(message = "MobileNumber must not be null")
	private String mobileNumber;

	private LocalDate dateOfBirth;

	@Size(min = 10, max = 10, message = "PanCard must be of length 10")
	@NotNull(message = "PanCard must not be null")
	private String panCard;

	private String citizenship;

	private String status;
	
	@Digits(fraction = 2, integer = 10)
	@NotNull(message = "AnnualIncome must not be null")
	private Double annualIncome;
	
	@NotEmpty(message = "Address must not be empty")
	private Set<AddressDTO> address;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCardRequestDTO other = (CreditCardRequestDTO) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(emailId, other.emailId)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(mobileNumber, other.mobileNumber)
				&& Objects.equals(panCard, other.panCard);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, emailId, fullName, mobileNumber, panCard);
	}
	
	
	
}
