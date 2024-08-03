package com.apigateway.apigateway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apigateway.apigateway.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	UserEntity save(UserEntity user);
	
	Optional<UserEntity> findByUsername(String username);

}
