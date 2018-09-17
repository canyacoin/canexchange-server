package com.canya.gateway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.canya.gateway.web.rest.util.TokenAddress;

/**
 * Spring Data MongoDB repository for the User entity.
 */
@Repository
public interface TokenRepository extends MongoRepository<TokenAddress, String> {

}
