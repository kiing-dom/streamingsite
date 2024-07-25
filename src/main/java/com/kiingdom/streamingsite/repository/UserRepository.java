package com.kiingdom.streamingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiingdom.streamingsite.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
