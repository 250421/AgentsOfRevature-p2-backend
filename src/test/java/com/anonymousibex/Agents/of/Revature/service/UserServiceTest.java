package com.anonymousibex.Agents.of.Revature.service;

import com.anonymousibex.Agents.of.Revature.dto.UserDto;
import com.anonymousibex.Agents.of.Revature.exception.*;
import com.anonymousibex.Agents.of.Revature.model.User;
import com.anonymousibex.Agents.of.Revature.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("Password123!");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(encoder.encode("Password123!")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.createUser(user);

        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(encoder, times(1)).encode("Password123!");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_UsernameAlreadyExists() {
        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(user));
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testCreateUser_InvalidUserName() {
        User user = new User();
        user.setUsername("ter");

        when(userRepository.findByUsername("ter")).thenReturn(Optional.empty());

        assertThrows(InvalidUsernameException.class, () -> userService.createUser(user));
        verify(userRepository, times(1)).findByUsername("ter");
    }

    @Test
    void testCreateUser_InvalidPassword() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("Invalid");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(InvalidPasswordException.class, () -> userService.createUser(user));
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testAuthenticate_Success() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(encoder.matches("Password123!", "encodedPassword")).thenReturn(true);

        User result = userService.authenticate("testuser", "Password123!");

        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(encoder, times(1)).matches("Password123!", "encodedPassword");
    }

    @Test
    void testAuthenticate_InvalidCredentials() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> userService.authenticate("testuser", "Password123!"));
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testGetCurrentUserBySession_Success() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1L);
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getCurrentUserBySession(request);

        assertNotNull(result);

    }

    @Test
    void testGetCurrentUserBySession_NoSession() {
        when(request.getSession(false)).thenReturn(null);

        assertThrows(NoActiveSessionException.class, () -> userService.getCurrentUserBySession(request));
        verify(request, times(1)).getSession(false);
    }

    @Test
    void testGetCurrentUserBySession_UserNotFound() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.getCurrentUserBySession(request));

    }

    @Test
    void testEnsureNoActiveSession_NoSession() {
        when(request.getSession(false)).thenReturn(null);

        assertDoesNotThrow(() -> userService.ensureNoActiveSession(request));
        verify(request, times(1)).getSession(false);
    }

    @Test
    void testEnsureNoActiveSession_SessionExists() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1L);

        assertThrows(SessionAlreadyExistsException.class, () -> userService.ensureNoActiveSession(request));
        verify(request, times(1)).getSession(false);
        verify(session, times(1)).getAttribute("userId");
    }

    @Test
    void testEnsureActiveSession_Success() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1L);

        assertDoesNotThrow(() -> userService.ensureActiveSession(request));
        verify(request, times(1)).getSession(false);
        verify(session, times(1)).getAttribute("userId");
    }

    @Test
    void testEnsureActiveSession_NoSession() {
        when(request.getSession(false)).thenReturn(null);

        assertThrows(NoActiveSessionException.class, () -> userService.ensureActiveSession(request));
        verify(request, times(1)).getSession(false);
    }

    @Test
    void testEnsureActiveSession_NoUserId() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(null);

        assertThrows(NoActiveSessionException.class, () -> userService.ensureActiveSession(request));
        verify(request, times(1)).getSession(false);
        verify(session, times(1)).getAttribute("userId");
    }
}
