package com.example.creditcardservice.entity;

import javax.persistence.PostLoad;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.creditcardservice.util.Encryptor;

/**
 * EntityListener for CreditCardRequest to encryption and decryption 
 * @author javadevopsmc06
 *
 */
public class CreditCardRequestListener {
	@Autowired
	private Encryptor encryptor;
	
	@PrePersist
	@PreUpdate
	public void encryptPassword(Object obj) {
		if(!(obj instanceof CreditCardRequest))
			return;
		CreditCardRequest entity = (CreditCardRequest) obj;
		entity.setEpanCard(null);
		
		String encryptedValue = encryptor.encryptColumn(entity.getPanCard());
		entity.setEpanCard(encryptedValue);
	}
	
	@PostLoad
	@PostUpdate
	public void decryptPassword(Object obj) {
		if(!(obj instanceof CreditCardRequest))
			return;
		CreditCardRequest entity = (CreditCardRequest) obj;
		entity.setPanCard(null);
		
		String decryptedValue = encryptor.decryptColumn(entity.getEpanCard());
		entity.setPanCard(decryptedValue);
	}
}
