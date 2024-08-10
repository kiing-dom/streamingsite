package com.kiingdom.streamingsite.service.auth.login;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kiingdom.streamingsite.model.User;

@Service
public interface LoginService {
    ResponseEntity<?> login(User user);
}
