package com.anonymousibex.Agents.of.Revature.config;

import com.anonymousibex.Agents.of.Revature.model.User;
import com.anonymousibex.Agents.of.Revature.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionAuthenticationFilterTest {

    private SessionAuthenticationFilter filter;
    private UserRepository userRepo;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepository.class);
        filter = new SessionAuthenticationFilter(userRepo);
        // clear SecurityContext between tests
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void whenSessionContainsUserId_thenSecurityContextIsPopulated() throws Exception {
        // arrange
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        // put userId in session
        req.getSession(true).setAttribute("userId", 123L);

        User u = new User();
        u.setId(123L);
        u.setUsername("bob");
        when(userRepo.findById(123L)).thenReturn(Optional.of(u));

        // act
        filter.doFilterInternal(req, res, chain);

        // assert
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(auth).isNotNull();
        assertThat(auth.isAuthenticated()).isTrue();
        assertThat(auth.getPrincipal()).isSameAs(u);

        // and chain always proceeds
        verify(chain).doFilter(req, res);
    }

    @Test
    void whenNoSession_thenDoesNotAuthenticate() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilterInternal(req, res, chain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(chain).doFilter(req, res);
    }

    @Test
    void whenSessionButNoUser_thenDoesNotAuthenticate() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        req.getSession(true).setAttribute("userId", 999L);
        when(userRepo.findById(999L)).thenReturn(Optional.empty());

        filter.doFilterInternal(req, res, chain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(chain).doFilter(req, res);
    }
}
