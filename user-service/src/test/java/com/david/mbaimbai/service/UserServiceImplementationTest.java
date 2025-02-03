package com.david.mbaimbai.service;

import com.david.mbaimbai.config.JwtProvider;
import com.david.mbaimbai.entity.User;
import com.david.mbaimbai.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserServiceImplementationTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImplementation userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserProfile() {
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIn0.-4PxPfZpNzNkIwYwM5PbqFb3q0IhxXL2rXvJ9wFxIkU";
        String email = "david.mbaimbai@gmail.com";
        User mockUser = new User();
        mockUser.setEmail(email);

        try (MockedStatic<JwtProvider> mockedJwtProvider = mockStatic(JwtProvider.class)) {
            // Mock static method
            mockedJwtProvider.when(() -> JwtProvider.getEmailFromJwtToken(jwt)).thenReturn(email);

            when(userRepository.findByEmail(email)).thenReturn(mockUser);

            // Call the method under test
            User result = userService.getUserProfile(jwt);

            // Assertions
            assertNotNull(result);
            assertEquals(email, result.getEmail());

            // Verifications
            mockedJwtProvider.verify(() -> JwtProvider.getEmailFromJwtToken(jwt), times(1));
            verify(userRepository, times(1)).findByEmail(email);
        }
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setEmail("david.mbaimbai@gmail.com");
        User user2 = new User();
        user2.setEmail("davymbaimbai@gmail.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Call the method under test
        List<User> result = userService.getAllUsers();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("david.mbaimbai@gmail.com", result.get(0).getEmail());
        assertEquals("davymbaimbai@gmail.com", result.get(1).getEmail());

        // Verifications
        verify(userRepository, times(1)).findAll();
    }
}
