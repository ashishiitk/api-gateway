package com.apigateway.apigateway;

import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.apigateway.apigateway.service.JWTAuthenticationFilter;
import com.apigateway.apigateway.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JWTAuthenticationFilter jWTAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity.csrf(AbstractHttpConfigurer::disable)
				           .authorizeHttpRequests(registry -> {
			                                      registry.requestMatchers("/auth/**","auth/login").permitAll();
			                                      registry.requestMatchers("/employee/**./department/**").authenticated();
			                                      registry.anyRequest().authenticated();
		                    })
				           //.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
				           .addFilterBefore(jWTAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				           .build();
	}

	/*
	 * @Bean public UserDetailsService userDetailService() { UserDetails normalUser
	 * = User.builder() .username("ashish")
	 * .password(passwordEncoder().encode("1234")) .roles("ADMIN") .build();
	 * 
	 * UserDetails adminUser = User.builder() .username("yadav")
	 * .password(passwordEncoder().encode("1234")) .roles("USER","ADMIN") .build();
	 * return new InMemoryUserDetailsManager(normalUser, adminUser); }
	 */

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(getUserDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(authenticationProvider());
	}
	
	public UserDetailsService getUserDetailsService() {
		return myUserDetailsService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
