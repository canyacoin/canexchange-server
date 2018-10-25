package com.canya.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canya.gateway.domain.Config;

/**
 * Spring Data MongoDB repository for the User entity.
 */
@Repository
public interface ConfigRepository extends JpaRepository<Config, String> {

	Config findOneByKey(String address);
}
