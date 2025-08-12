package com.userdatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @GetMapping
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String authHeader) {
        // 1️⃣ Check if Authorization header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid Authorization header");
        }

        // 2️⃣ Extract token (remove "Bearer ")
        String token = authHeader.substring(7);

        // 3️⃣ Validate token
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
        }

        // 4️⃣ Extract username from token
        String username = jwtUtil.getUsernameFromToken(token);

        // 5️⃣ Fetch user details from DB (must return Optional<UserDetails>)
        Optional<UserDetails> userDetails = userDetailsRepository.findByUsername(username);

        // 6️⃣ Return details if found, else return NOT_FOUND
        return userDetails
                .<ResponseEntity<?>>map(details -> ResponseEntity.ok(details))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User details not found"));
    }
}
