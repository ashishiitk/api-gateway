package com.apigateway.apigateway.entity;

import lombok.Data;

@Data
public class AuthenticationRequest {
	
	private String username;
    private String password;
}
