package com.apigateway.apigateway;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;

public class SecretKeyMakerTest {
	
	@Test
	public void createSecretKey() {
		SecretKey encodedKey = Jwts.SIG.HS256.key().build();
		String key = DatatypeConverter.printHexBinary(encodedKey.getEncoded());
		System.out.println("Key : "+key);
	}
}
