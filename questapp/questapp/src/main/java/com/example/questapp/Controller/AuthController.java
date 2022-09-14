package com.example.questapp.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.questapp.model.RefreshToken;
import com.example.questapp.model.User;
import com.example.questapp.request.RefreshRequest;
import com.example.questapp.request.UserRequest;
import com.example.questapp.responses.AuthResponse;
import com.example.questapp.security.JwtTokenProvider;
import com.example.questapp.services.RefreshTokenService;
import com.example.questapp.services.UserService;



@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
		
		private AuthenticationManager authenticationManager;
		
		private JwtTokenProvider jwtTokenProvider;
		
		private UserService userService;
		
		private PasswordEncoder passwordEncoder;

		private RefreshTokenService refreshTokenService;
		
	    public AuthController(AuthenticationManager authenticationManager, UserService userService, 
	    		PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
	        this.authenticationManager = authenticationManager;
	        this.userService = userService;
	        this.passwordEncoder = passwordEncoder;
	        this.jwtTokenProvider = jwtTokenProvider;
	        this.refreshTokenService = refreshTokenService;
	    }
	    
		@PostMapping("/login")
		public AuthResponse login(@RequestBody UserRequest loginRequest) {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
			Authentication auth = authenticationManager.authenticate(authToken);
			SecurityContextHolder.getContext().setAuthentication(auth);
			String jwtToken = jwtTokenProvider.generateJwtToken(auth);
			User user = userService.getOneUserByUserName(loginRequest.getUserName());
			AuthResponse authResponse = new AuthResponse();
			authResponse.setAccessToken("Bearer " + jwtToken);
			authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
			authResponse.setUserId(user.getId());
			return authResponse;
		}
		
		@PostMapping("/register")
		public ResponseEntity<AuthResponse> register(@RequestBody UserRequest registerRequest) {
			AuthResponse authResponse = new AuthResponse();
			if(userService.getOneUserByUserName(registerRequest.getUserName()) != null) {
				authResponse.setMessage("Username already in use.");
				return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
			}
			
			User user = new User();
			user.setUserName(registerRequest.getUserName());
			user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
			userService.add(user);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerRequest.getUserName(), registerRequest.getPassword());
			Authentication auth = authenticationManager.authenticate(authToken);
			SecurityContextHolder.getContext().setAuthentication(auth);
			String jwtToken = jwtTokenProvider.generateJwtToken(auth);
			
			authResponse.setMessage("User successfully registered.");
			authResponse.setAccessToken("Bearer " + jwtToken);
			authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
			authResponse.setUserId(user.getId());
			return new ResponseEntity<>(authResponse, HttpStatus.CREATED);		
		}
		
		@PostMapping("/refresh")
		public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
			AuthResponse response = new AuthResponse();
			RefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());
			if(token.getToken().equals(refreshRequest.getRefreshToken()) &&
					!refreshTokenService.isRefreshExpired(token)) {

				User user = token.getUser();
				String jwtToken = jwtTokenProvider.generateJwtTokenByUserId(user.getId());
				response.setMessage("token successfully refreshed.");
				response.setAccessToken("Bearer " + jwtToken);
				response.setUserId(user.getId());
				return new ResponseEntity<>(response, HttpStatus.OK);		
			} else {
				response.setMessage("refresh token is not valid.");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}
			
		}
		

	}

