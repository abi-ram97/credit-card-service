package com.example.creditcardservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Persistence class for Address
 * @author javadevopsmc06
 *
 */
@Entity
@Table(name = "ADDRESS", schema = "MCDBO")
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String street;
	private String area;
	private String city;
	private String state;
	private Long pincode;
	private String type;
	
	@ManyToOne
	@JoinColumn(name = "REQUEST_ID", referencedColumnName = "REQUEST_ID")
	private CreditCardRequest request;
	
	public Address() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CreditCardRequest getRequest() {
		return request;
	}

	public void setRequest(CreditCardRequest request) {
		this.request = request;
	}

}
