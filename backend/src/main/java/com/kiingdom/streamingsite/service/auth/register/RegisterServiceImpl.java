package com.kiingdom.streamingsite.service.auth.register;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kiingdom.streamingsite.model.User;
import com.kiingdom.streamingsite.repository.UserRepository;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;

    public RegisterServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<String> register(User user) {
        try{
        userRepository.save(user);
        return ResponseEntity.ok("User registered Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occured while registering the user");
        }
    }
    
}
