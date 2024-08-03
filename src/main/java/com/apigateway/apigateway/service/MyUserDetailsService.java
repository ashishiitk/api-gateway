package com.apigateway.apigateway.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apigateway.apigateway.entity.UserEntity;
import com.apigateway.apigateway.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = repository.findByUsername(username);
		if(user.isPresent()) {
			var userObj = user.get();
			return User.builder()
					.username(userObj.getUsername())
					.password(userObj.getPassword())
					.roles(getUserRolls(userObj))
					.build();
		}else if(!user.isPresent()){
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return null;
	}
	
	private String[] getUserRolls(UserEntity user) {
		if(user.getRolls() == null) {
			return new String[]{"USER"};
		}else {
			return user.getRolls().split(",");
		}
		
	}

}