package com.canya.gateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.canya.gateway.domain.Transaction;
import com.canya.gateway.repository.AuthorityRepository;
import com.canya.gateway.repository.TransactionRepository;
import com.canya.gateway.repository.UserRepository;
import com.canya.gateway.service.dto.TransactionDTO;

/**
 * Service class for managing users.
 */
@Service
public class TransactionService {

	private final Logger log = LoggerFactory.getLogger(TransactionService.class);

	private final UserRepository userRepository;

	private final TransactionRepository transactionRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthorityRepository authorityRepository;


	public TransactionService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthorityRepository authorityRepository,  
			TransactionRepository transactionRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authorityRepository = authorityRepository;
		this.transactionRepository = transactionRepository;
	}

	public Transaction saveTransaction(TransactionDTO transactionDTO) {
		Transaction transaction = new Transaction();
		transaction.setAccept(transactionDTO.isAccept());
		transaction.setAddress(transactionDTO.getAddress());
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setCurrency(transactionDTO.getCurrency());
		transaction.setDate(transactionDTO.getDate());
		transaction.setEmail(transactionDTO.getEmail());
		transaction.setEth(transactionDTO.getEth());
		transaction.setHash(transactionDTO.getHash());
		transaction.setHashethertoaccount(transactionDTO.getHashethertoaccount());
		transaction.setOrderid(transactionDTO.getKey());
		transaction.setStatus(transactionDTO.getStatus());
		transaction.setUsd(transactionDTO.getUsd());
		
		transactionRepository.save(transaction);

		log.debug("data saved {}", transaction.getOrderid());
		return transaction;
	}

	public Transaction updateTransaction(Transaction transactionDTO) {

		return transactionRepository.save(transactionDTO);
	}

	public Transaction findOneByOrderid(String key) {
		return transactionRepository.findOneByOrderid(key);
	}

}
