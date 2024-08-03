package com.apigateway.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apigateway.apigateway.entity.AuthenticationRequest;
import com.apigateway.apigateway.entity.AuthenticationResponse;
import com.apigateway.apigateway.entity.UserEntity;
import com.apigateway.apigateway.repository.UserRepository;
import com.apigateway.apigateway.service.JWTService;
import com.apigateway.apigateway.service.MyUserDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Tutorial", description = "Tutorial management APIs")
public class LoginController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired(required=true)
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JWTService jwtService;
	
	
	@PostMapping("/add")
	public ResponseEntity<?> saveUser(@RequestBody UserEntity user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return ResponseEntity.ok("User registered successfully.");
	}
	
	@Operation(
		      summary = "Retrieve a Tutorial by Id",
		      description = "Get a Tutorial object by specifying its id. The response is Tutorial object with id, title, description and published status.",
		      tags = { "tutorials", "get" })
    

	@PostMapping("/login")
	public AuthenticationResponse getAuthentication(@RequestBody AuthenticationRequest authRequest) {
		Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
		if(authentication.isAuthenticated()) {
			String jwtToken = jwtService.generateJwtToken(myUserDetailsService.loadUserByUsername(authRequest.getUsername()));
			return new AuthenticationResponse(jwtToken);
		}else {
			throw new UsernameNotFoundException(authRequest.getUsername() + "not found.");
		}
	}

}
