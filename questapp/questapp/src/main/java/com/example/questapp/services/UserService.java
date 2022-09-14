package com.example.questapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.questapp.Repository.UserRepository;
import com.example.questapp.model.User;

@Service
public class UserService {

	UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User add(User newUser) {
		return userRepository.save(newUser);
	}

	public User getOneUser(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	public User updateUser(Long userId, User newUser) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User foundUser = user.get();
			foundUser.setUserName(newUser.getUserName());
			foundUser.setPassword(newUser.getPassword());
			userRepository.save(foundUser);
			return foundUser;
		} else
			return null;
	}

	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}

	public User getOneUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

}















