package com.example.questapp.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questapp.model.User;

public interface UserRepository  extends JpaRepository<User, Long>{

	User findByUserName(String userName);

}
