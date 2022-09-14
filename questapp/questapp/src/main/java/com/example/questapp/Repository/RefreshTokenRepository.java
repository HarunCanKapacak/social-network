package com.example.questapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questapp.model.RefreshToken;



public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

	RefreshToken findByUserId(Long userId);
	
}
