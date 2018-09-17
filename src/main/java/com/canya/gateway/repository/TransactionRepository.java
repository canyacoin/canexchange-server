package com.canya.gateway.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.canya.gateway.domain.Transaction;

/**
 * Spring Data MongoDB repository for the User entity.
 */
@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findOneByAddressAndAmount(String address, String amount);

    Transaction findOneByKey(String key);
}
