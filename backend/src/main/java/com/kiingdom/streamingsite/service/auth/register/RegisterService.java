package com.kiingdom.streamingsite.service.auth.register;

import org.springframework.http.ResponseEntity;

import com.kiingdom.streamingsite.model.User;

public interface RegisterService {
    ResponseEntity<String> register(User user);
}
