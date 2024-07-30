package com.kiingdom.streamingsite.service.auth.login;

import org.springframework.http.ResponseEntity;

import com.kiingdom.streamingsite.model.User;

public interface LoginService {
    ResponseEntity<String> login(User user);
}
