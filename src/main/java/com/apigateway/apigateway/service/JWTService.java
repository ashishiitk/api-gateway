package com.apigateway.apigateway.service;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

		private static final String  secrect_key="8497BF8A0155F81B9DC23E9727926E79E3E7CE8A5C34B1397349F0BF363175AE";
		private static final Long VALIDITY = TimeUnit.MINUTES.toMillis(30000);
		
		public String generateJwtToken(UserDetails userDetails) {
			Map<String, String> claims = new HashMap<String, String>();
			claims.put("issuer", "http://ashish.com");
			return Jwts.builder()
					.claims(claims)
					.subject(userDetails.getUsername())
					.signWith(generateKey())
					.issuedAt(Date.from(Instant.now()))
					.expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
					.compact();
											
		}
		
		private SecretKey generateKey() {
	        byte[] decodedKey = Base64.getDecoder().decode(secrect_key);
	        return Keys.hmacShaKeyFor(decodedKey);
	    }
		
		public String extractUserName(String jwt) {
			Claims claims = getClaims(jwt);
			return claims.getSubject();			
		}
		
		public Claims getClaims(String jwt) {
			return Jwts.parser().verifyWith(generateKey())
					.build()
					.parseSignedClaims(jwt)
					.getPayload();
		}
		
		public boolean isTokenValid(String jwt) {
			Claims claims = getClaims(jwt);
			return claims.getExpiration().after(Date.from(Instant.now()));		
		}
		
}
