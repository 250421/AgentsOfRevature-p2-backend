package com.anonymousibex.Agents.of.Revature.service;

import com.anonymousibex.Agents.of.Revature.exception.NoUserResultsFoundException;
import com.anonymousibex.Agents.of.Revature.model.Results;
import com.anonymousibex.Agents.of.Revature.model.Scenario;
import com.anonymousibex.Agents.of.Revature.repository.ResultsRepository;
import com.anonymousibex.Agents.of.Revature.repository.ScenarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResultsServiceTest {

    @Mock
    private ResultsRepository resultsRepository;

    @Mock
    private ScenarioRepository scenarioRepository;

    @InjectMocks
    private ResultsService resultsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllResults() {
        Results result1 = new Results();
        Results result2 = new Results();
        when(resultsRepository.findAll()).thenReturn(Arrays.asList(result1, result2));

        List<Results> results = resultsService.getAllResults();

        assertNotNull(results);
        assertEquals(2, results.size());
        verify(resultsRepository, times(1)).findAll();
    }

    @Test
    void testGetAllUserResults_Success() {
        Results result1 = new Results();
        Results result2 = new Results();
        when(resultsRepository.findByUserId(1L)).thenReturn(Optional.of(Arrays.asList(result1, result2)));

        List<Results> results = resultsService.getAllUserResults(1L);

        assertNotNull(results);
        assertEquals(2, results.size());
        verify(resultsRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetAllUserResults_NoResults() {
        when(resultsRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(NoUserResultsFoundException.class, () -> resultsService.getAllUserResults(1L));
        verify(resultsRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testAddResult() {
        Scenario scenario = new Scenario();
        Results result = new Results();
        when(resultsRepository.save(any(Results.class))).thenReturn(result);

        Results savedResult = resultsService.addResult(scenario, true, 10);

        assertNotNull(savedResult);
        verify(resultsRepository, times(1)).save(any(Results.class));
    }

    @Test
    void testUpdateResult_Success() {
        Results existingResult = new Results();
        existingResult.setId(1L);
        when(resultsRepository.findById(1L)).thenReturn(Optional.of(existingResult));

        Results updatedResult = new Results();
        updatedResult.setId(1L);
        updatedResult.setRepGained(20);
        updatedResult.setDidWin(true);

        Results result = resultsService.UpdateResult(updatedResult);

        assertNotNull(result);
        assertEquals(20, result.getRepGained());
        assertTrue(result.isDidWin());
    }

    @Test
    void testUpdateResult_NoResultFound() {
        Results updatedResult = new Results();
        updatedResult.setId(1L);
        when(resultsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoUserResultsFoundException.class, () -> resultsService.UpdateResult(updatedResult));
    }
}
