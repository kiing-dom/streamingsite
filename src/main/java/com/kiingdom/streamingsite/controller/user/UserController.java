package com.kiingdom.streamingsite.controller.user;

import java.util.List;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiingdom.streamingsite.model.User;
import com.kiingdom.streamingsite.service.user.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

        @PostMapping
        public ResponseEntity<User> createUser(@RequestBody User user) {
            User createdUser = userService.saveUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
        public ResponseEntity<User> getUserById(@PathVariable Long id) {
            Optional<User> user = userService.getUserById(id);
            return user.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @GetMapping("/username/{username}")
        public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
            Optional<User> user = userService.getUserByUsername(username);
            return user.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @GetMapping("/email/{email}")
        public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
            Optional<User> user = userService.getUserByEmail(email);
            return user.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @GetMapping
        public ResponseEntity<List<User>> getAllUsers() {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
