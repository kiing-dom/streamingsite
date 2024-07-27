package com.kiingdom.streamingsite.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.kiingdom.streamingsite.model.User;
import com.kiingdom.streamingsite.repository.UserRepository;
import com.kiingdom.streamingsite.service.implementation.UserServiceImpl;



class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(123L);
        testUser.setUsername("testuser123");
        testUser.setEmail("testuser123@test.com");
        testUser.setPassword("testpassword123");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(testUser)).thenReturn((testUser));

        User savedUser = userServiceImpl.saveUser(testUser);

        assertNotNull(savedUser);
        assertEquals("testuser123", savedUser.getUsername());
    }
    
    @Test
    void testGetUserById() {
       when(userRepository.findById(123L)).thenReturn(Optional.of(testUser));

       Optional<User> foundUser = userServiceImpl.getUserById(123L);

       assertTrue(foundUser.isPresent());
       assertEquals("testuser123", foundUser.get().getUsername());
       verify(userRepository, times(1)).findById(123L);
    }

    @Test
    void testGetUserByUsername() {
        when(userRepository.findByUsername("testuser123")).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userServiceImpl.getUserByUsername("testuser123");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser123", foundUser.get().getUsername());
        verify(userRepository, times(1)).findByUsername("testuser123");
    }

    @Test
    void testGetByEmail() {
        when(userRepository.findByEmail("testuser123@test.com")).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userServiceImpl.getUserByEmail("testuser123@test.com");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser123@test.com", foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail("testuser123@test.com");
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(testUser));

        List<User> users = userServiceImpl.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("testuser123", users.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(123L);
        
        userServiceImpl.deleteUser(123L);

        verify(userRepository, times(1)).deleteById(123L);

    }
}
