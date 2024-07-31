package com.kiingdom.streamingsite.service.auth.register;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kiingdom.streamingsite.model.User;
import com.kiingdom.streamingsite.repository.UserRepository;

@Service
public class RegisterServiceImpl implements RegisterService {

    private UserRepository userRepository;

    @Override
    public ResponseEntity<String> register(User user) {
        userRepository.save(user);
        return ResponseEntity.ok("User registered Successfully");
    }
    
}
