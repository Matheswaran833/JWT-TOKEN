package com.userdatabase;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;

    // Create signing key from secret
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Generate JWT
    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(username)        // set subject
                .issuedAt(now)            // issue date
                .expiration(expiry)       // expiration date
                .signWith(getSigningKey()) // sign
                .compact();
    }

    // Extract username from token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()                      // new API
                .verifyWith(getSigningKey())               // verify signature
                .build()
                .parseSignedClaims(token)                   // parse claims
                .getPayload();
        return claims.getSubject();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
