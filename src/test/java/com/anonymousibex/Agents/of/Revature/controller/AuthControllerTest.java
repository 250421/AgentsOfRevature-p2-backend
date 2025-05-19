package com.anonymousibex.Agents.of.Revature.controller;

import com.anonymousibex.Agents.of.Revature.dto.ResponseDto;
import com.anonymousibex.Agents.of.Revature.dto.UserDto;
import com.anonymousibex.Agents.of.Revature.model.User;
import com.anonymousibex.Agents.of.Revature.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock private UserService userService;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;

    @InjectMocks private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup() {
        User user = new User();
        UserDto userDto = new UserDto(1L,"agent007");
        when(userService.createUser(user)).thenReturn(userDto);

        ResponseEntity<UserDto> result = authController.signup(user);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(userDto, result.getBody());
    }

    @Test
    void testLogin() {
        User user = new User();
        user.setUsername("agent007");
        user.setPassword("Test@123");

        User authUser = new User();
        authUser.setId(1L);

        when(userService.authenticate(user.getUsername(), user.getPassword())).thenReturn(authUser);
        when(request.getSession(true)).thenReturn(session);

        ResponseEntity<ResponseDto> result = authController.login(user, request);

        verify(session).setAttribute("userId", 1L);
        assertEquals("Login successful", result.getBody().getMessage());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testLogout() {
        when(request.getSession(false)).thenReturn(session);

        ResponseEntity<ResponseDto> result = authController.logout(request, response);

        verify(session).invalidate();
        assertEquals("Logout successful", result.getBody().getMessage());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetCurrentUser() {
        User user = new User();
        user.setId(42L);
        user.setUsername("agent007");

        UserDto dto = new UserDto(42L, "agent007");

        when(userService.getCurrentUserBySession(request)).thenReturn(user);

        ResponseEntity<UserDto> result = authController.getCurrentUser(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("agent007", result.getBody().getUsername());
    }
}
