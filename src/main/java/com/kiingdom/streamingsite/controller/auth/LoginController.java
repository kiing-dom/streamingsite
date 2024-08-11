package com.kiingdom.streamingsite.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiingdom.streamingsite.model.User;
import com.kiingdom.streamingsite.service.auth.login.LoginService;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        return loginService.login(user);
    }
}
