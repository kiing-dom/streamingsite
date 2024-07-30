package com.kiingdom.streamingsite.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiingdom.streamingsite.model.User;
import com.kiingdom.streamingsite.service.auth.register.RegisterService;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return registerService.register(user);
    }
}
