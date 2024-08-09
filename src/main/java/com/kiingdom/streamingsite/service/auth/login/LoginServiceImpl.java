package com.kiingdom.streamingsite.service.auth.login;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kiingdom.streamingsite.model.User;
import com.kiingdom.streamingsite.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService{

    private final UserRepository userRepository;

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<String> login(User user) {
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        if(foundUser.isPresent() && foundUser.get().getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }


    
}