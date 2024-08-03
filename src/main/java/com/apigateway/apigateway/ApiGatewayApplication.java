package com.apigateway.apigateway;

import javax.sound.midi.Soundbank;

import org.hibernate.boot.model.source.internal.hbm.RelationalValueSourceHelper.AbstractColumnsAndFormulasSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	

}
