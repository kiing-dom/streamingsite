package com.kiingdom.streamingsite.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kiingdom.streamingsite.model.User;
import com.kiingdom.streamingsite.service.UserService;

@SpringBootTest
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User testUser;
    private List<User> userList;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setEmail("testUser@example.com");

        userList = new ArrayList<>();
        userList.add(testUser);
    }

    @Test
    void testCreateUser_success() {
        when(userService.saveUser(any(User.class))).thenReturn(testUser);

        ResponseEntity<User> response = userController.createUser(testUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testUser, response.getBody());

        verify(userService).saveUser(testUser);
    }

    @Test
    void testGetUserById_success() {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(testUser));

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testUser, response.getBody());

        verify(userService).getUserById(1L);
    }

    @Test
    void testGetUserById_notFound() {
        when(userService.getUserById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUserByUsername_success() {
        when(userService.getUserByUsername(any(String.class))).thenReturn(Optional.of(testUser));

        ResponseEntity<User> response = userController.getUserByUsername("testUser");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testUser, response.getBody());

        verify(userService).getUserByUsername("testUser");
    }

    @Test
    void testGetUserByUsername_notFound() {
        when(userService.getUserByUsername(any(String.class))).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserByUsername("testUser");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUserByEmail_success() {
        when(userService.getUserByEmail(any(String.class))).thenReturn(Optional.of(testUser));

        ResponseEntity<User> response = userController.getUserByEmail("testUser@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testUser, response.getBody());

        verify(userService).getUserByEmail("testUser@example.com");
    }

    @Test
    void testGetUserByEmail_notFound() {
        when(userService.getUserByEmail(any(String.class))).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserByEmail("testUser@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllUsers_success() {
        when(userService.getAllUsers()).thenReturn(userList);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(testUser, response.getBody().get(0));

        verify(userService).getAllUsers();
    }

    @Test
    void testDeleteUser_success() {
        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(userService).deleteUser(1L);
    }
}
