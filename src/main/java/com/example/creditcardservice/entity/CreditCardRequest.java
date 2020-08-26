package com.example.creditcardservice.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * Persistence class for CreditCardRequest
 * @author javadevopsmc06
 *
 */
@Entity
@Table(name = "CREDIT_CARD_REQUEST", schema = "MCDBO")
@Data
@EntityListeners(CreditCardRequestListener.class)
public class CreditCardRequest {
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "REQUEST_ID")
	private String requestId;
	
	private String customerId;
	
	private String approvalId;
	
	private String fullName;
	
	private String emailId;
	
	private String mobileNumber;
	
	private LocalDate dateOfBirth;
	
	@Transient
	private String panCard;
	
	@Column(name = "PANCARD")
	private String epanCard;
	
	private String citizenship;
	
	private Double annualIncome;
	
	private String status;
	
	@OneToMany(mappedBy = "request", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Address> address = new HashSet<>();
	
	public void addAddress(Address address1) {
		address.add(address1);
		address1.setRequest(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCardRequest other = (CreditCardRequest) obj;
		return Objects.equals(annualIncome, other.annualIncome) && Objects.equals(requestId, other.requestId)
				&& Objects.equals(approvalId, other.approvalId) && Objects.equals(customerId, other.customerId)
				&& Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(emailId, other.emailId)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(mobileNumber, other.mobileNumber)
				&& Objects.equals(panCard, other.panCard) && Objects.equals(status, other.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(annualIncome, requestId, approvalId, customerId, dateOfBirth, emailId, fullName,
				mobileNumber, panCard, status);
	}

}
