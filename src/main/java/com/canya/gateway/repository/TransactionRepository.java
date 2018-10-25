package com.canya.gateway.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canya.gateway.domain.Transaction;

/**
 * Spring Data MongoDB repository for the User entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findOneByAddressAndAmount(String address, String amount);

    Transaction findOneByOrderid(String key);
}
