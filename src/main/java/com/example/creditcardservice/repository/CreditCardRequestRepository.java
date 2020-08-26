package com.example.creditcardservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.creditcardservice.entity.CreditCardRequest;

@Repository
public interface CreditCardRequestRepository extends JpaRepository<CreditCardRequest, String> {

}
