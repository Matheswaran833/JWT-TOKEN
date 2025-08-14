package com.SpringBoot.Security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private static final String SECRET_KEY_STRING= "ygvVeI4QNE5LwOabrE9flHxyfMHEhyjd";
	
	private final SecretKey SECRET_KEY= Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+1000 *60 *60))
				.signWith(SECRET_KEY,Jwts.SIG.HS256)
				.compact();
	}
	
	public boolean ValidateToken(String token ,UserDetails userdetails) {
		return extractUsername(token).equals(userdetails.getUsername());
	}
	
	public String extractUsername(String token) {
		return Jwts.parser()
				.verifyWith(SECRET_KEY)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	

}
