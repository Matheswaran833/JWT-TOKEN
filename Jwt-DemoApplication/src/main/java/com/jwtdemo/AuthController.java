package com.jwtdemo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        // Dummy authentication check
        if ("madhu".equals(username) && "madhu123".equals(password)) {
            String token = JwtUtil.generateToken(username);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }

    @GetMapping("/secure-data")
    public ResponseEntity<?> getSecureData(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (JwtUtil.validateToken(token)) {
            String username = JwtUtil.getUsername(token);
            return ResponseEntity.ok("Secure data for " + username);
        }
        return ResponseEntity.status(401).body("Invalid or expired token");
    }
}