package com.canya.gateway.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canya.gateway.domain.TokenAddress;

/**
 * Spring Data MongoDB repository for the User entity.
 */
@Repository
public interface TokenRepository extends JpaRepository<TokenAddress, String> {
	List<TokenAddress> findAllByStatus(Integer status);

	TokenAddress findOneByAddress(String address);
}
