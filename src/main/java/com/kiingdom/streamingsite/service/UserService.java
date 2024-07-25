package com.kiingdom.streamingsite.service;
import com.kiingdom.streamingsite.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    User saveUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    void deleteUser(Long id);
}
