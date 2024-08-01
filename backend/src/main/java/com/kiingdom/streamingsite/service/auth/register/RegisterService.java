package com.kiingdom.streamingsite.service.auth.register;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kiingdom.streamingsite.model.User;

@Service
public interface RegisterService {
    ResponseEntity<String> register(User user);
}
