package com.anonymousibex.Agents.of.Revature.service;

import com.anonymousibex.Agents.of.Revature.model.Calamity;
import com.anonymousibex.Agents.of.Revature.repository.CalamityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalamityServiceTest {

    @Mock
    private CalamityRepository calamityRepository;

    @InjectMocks
    private CalamityService calamityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllCalamities_ReturnsList() {
        Calamity calamity1 = new Calamity();
        Calamity calamity2 = new Calamity();
        when(calamityRepository.findAll()).thenReturn(Arrays.asList(calamity1, calamity2));

        List<Calamity> result = calamityService.findAllCalamities();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(calamityRepository, times(1)).findAll();
    }

    @Test
    void testFindAllCalamities_EmptyList() {
        when(calamityRepository.findAll()).thenReturn(List.of());

        List<Calamity> result = calamityService.findAllCalamities();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(calamityRepository, times(1)).findAll();
    }
}
