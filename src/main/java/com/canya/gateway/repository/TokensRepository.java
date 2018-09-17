package com.canya.gateway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.canya.gateway.domain.Tokens;

/**
 * Spring Data MongoDB repository for the User entity.
 */
@Repository
public interface TokensRepository extends MongoRepository<Tokens, String> {

	Tokens findOneByTokenid(String key);
}
