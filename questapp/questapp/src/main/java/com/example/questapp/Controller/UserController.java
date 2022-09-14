package com.example.questapp.Controller;

import java.util.List;



import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.questapp.model.User;
import com.example.questapp.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;


	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping()
	public List<User> getAll(){
		return userService.getAllUsers();
		
	}
	
	@PostMapping
	public User createUser(@RequestBody User newUser) {
		return userService.add(newUser);
		
	}
	
	@GetMapping("/{userId}")
	public User getOneUser(@PathVariable Long userId) {
		
		return userService.getOneUser(userId);
		
	}
	
	@PutMapping("/{userId}")
	public User updatedOneUser(@PathVariable Long userId,@RequestBody User newUser) {
	return userService.updateUser(userId,newUser);
		
	}
	
	@DeleteMapping("/{userId}")
	public void deleteOneUser(@PathVariable Long userId) {
		 userService.deleteUser(userId);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
