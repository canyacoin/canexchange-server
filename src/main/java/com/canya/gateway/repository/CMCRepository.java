package com.canya.gateway.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.canya.gateway.domain.Coinmarketcap;

/**
 * Spring Data MongoDB repository for the User entity.
 */
@Repository
public interface CMCRepository extends MongoRepository<Coinmarketcap, String> {

	List<Coinmarketcap> findAllByStatus(boolean status);

}
