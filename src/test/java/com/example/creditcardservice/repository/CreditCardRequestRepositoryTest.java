package com.example.creditcardservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.creditcardservice.entity.CreditCardRequest;
import com.example.creditcardservice.util.Encryptor;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(Encryptor.class)
class CreditCardRequestRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private CreditCardRequestRepository repository;

	@Test
	void testNeededObjects() {
		assertThat(entityManager).isNotNull();
		assertThat(repository).isNotNull();
	}

	@Test
	void testGetByRequestId() {
		CreditCardRequest request = new CreditCardRequest();
		request.setPanCard("pan123");
		CreditCardRequest saved = entityManager.persist(request);
		entityManager.flush();

		Optional<CreditCardRequest> optionalReq = repository.findById(saved.getRequestId());
		CreditCardRequest found = optionalReq.get();
		assertTrue(optionalReq.isPresent());
		assertNotNull(found, "RequestEntity is null");
		assertEquals("pan123", found.getPanCard(), "PanCard not matched");
	}

}
