package com.anonymousibex.Agents.of.Revature.controller;

import com.anonymousibex.Agents.of.Revature.model.Calamity;
import com.anonymousibex.Agents.of.Revature.service.CalamityService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalamityControllerTest {

    @Mock
    private CalamityService calamityService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CalamityController calamityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCalamities() {
        Calamity calamity1 = new Calamity();
        calamity1.setId(1L);
        calamity1.setTitle("Blackout");

        Calamity calamity2 = new Calamity();
        calamity2.setId(2L);
        calamity2.setTitle("Orbital Crash");

        List<Calamity> calamities = Arrays.asList(calamity1, calamity2);
        when(calamityService.findAllCalamities()).thenReturn(calamities);

        ResponseEntity<List<Calamity>> response = calamityController.getAllCalamities(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Blackout", response.getBody().get(0).getTitle());
        verify(calamityService, times(1)).findAllCalamities();
    }
}
