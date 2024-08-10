package com.kiingdom.streamingsite.service.auth.login;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kiingdom.streamingsite.model.User;
import com.kiingdom.streamingsite.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public LoginServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;

    }

    @Override
    public ResponseEntity<String> login(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User authenticatedUser = userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String role = authenticatedUser.getRole();

            if ("ADMIN".equals(role)) {
                return ResponseEntity.ok("Admin logged in successfully");
            } else if ("USER".equals(role)) {
                return ResponseEntity.ok("User logged in successfully");
            } else {
                return ResponseEntity.ok("Logged in successfully with unknown role");
            }

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}