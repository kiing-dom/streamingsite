package com.kiingdom.streamingsite.service;
import com.kiingdom.streamingsite.model.User;

import java.util.List;


public interface UserService {
    User saveUser(User user);
    User getUserById(Long id);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void deleteUser(Long id);
}