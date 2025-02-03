package com.david.mbaimbai.controller;

import com.david.mbaimbai.config.JwtProvider;
import com.david.mbaimbai.entity.User;
import com.david.mbaimbai.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserProfile_Success() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("david.mbaimbai@mail.com");
        String jwt = JwtProvider.generateToken(authentication);

        User user = new User();
        user.setEmail("david.mbaimbai@mail.com");

        when(userService.getUserProfile(jwt)).thenReturn(user);

        ResponseEntity<User> response = userController.getUserProfile(jwt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }


    @Test
    void testGetAllUsers_Success() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("david.mbaimbai@mail.com");
        String jwt = JwtProvider.generateToken(authentication);
        List<User> users = Arrays.asList(new User(), new User());

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getUsers(jwt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }
}
