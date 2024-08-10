package com.kiingdom.streamingsite.service.auth.register;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kiingdom.streamingsite.model.User;
import com.kiingdom.streamingsite.repository.UserRepository;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<String> register(User user) {
        try{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return ResponseEntity.ok("User registered Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occured while registering the user");
        }
    }
    
}
