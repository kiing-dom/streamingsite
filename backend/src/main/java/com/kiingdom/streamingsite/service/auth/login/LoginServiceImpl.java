package com.kiingdom.streamingsite.service.auth.login;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.kiingdom.streamingsite.model.User;
import com.kiingdom.streamingsite.repository.UserRepository;

public class LoginServiceImpl implements LoginService{

    private UserRepository userRepository;

    @Override
    public ResponseEntity<String> login(User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if(foundUser != null && foundUser.get().getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }


    
}