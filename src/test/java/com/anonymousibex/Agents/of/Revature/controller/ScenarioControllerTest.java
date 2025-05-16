package com.anonymousibex.Agents.of.Revature.controller;

import com.anonymousibex.Agents.of.Revature.controller.ResultsController;
import com.anonymousibex.Agents.of.Revature.dto.*;
import com.anonymousibex.Agents.of.Revature.model.Calamity;
import com.anonymousibex.Agents.of.Revature.model.HeroSelection;
import com.anonymousibex.Agents.of.Revature.model.Results;
import com.anonymousibex.Agents.of.Revature.model.User;
import com.anonymousibex.Agents.of.Revature.service.HeroSelectionService;
import com.anonymousibex.Agents.of.Revature.service.ResultsService;
import com.anonymousibex.Agents.of.Revature.service.ScenarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScenarioControllerTest {

    @InjectMocks
    private ScenarioController scenarioController;

    @Mock
    private ScenarioService scenarioService;

    @Mock
    private HttpServletRequest mockRequest;

    @Test
    void testStartScenario() {
        ScenarioRequestDto requestDto = new ScenarioRequestDto(1L, "Hero1", "Hero2", "Hero3");
        ScenarioDto expected = new ScenarioDto(1L, 1L, List.of("Hero1", "Hero2", "Hero3"), 0, 0, List.of(), null);

        when(scenarioService.startScenario(requestDto, mockRequest)).thenReturn(expected);

        ResponseEntity<ScenarioDto> response = scenarioController.startScenario(requestDto, mockRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void testContinueScenario() {
        Long scenarioId = 1L;
        ContinueScenarioRequest request = new ContinueScenarioRequest(1L);
        ScenarioDto updated = new ScenarioDto(1L, 1L, List.of("Hero1"), 10, 2, List.of(), null);

        when(scenarioService.continueScenario(scenarioId, request)).thenReturn(updated);

        ResponseEntity<ScenarioDto> response = scenarioController.continueScenario(scenarioId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
    }

    @Test
    void testGetScenarioById() {
        Long id = 1L;
        ScenarioDto dto = new ScenarioDto(1L, 1L, List.of("Hero1"), 5, 1, List.of(), null);

        when(scenarioService.getScenarioById(id)).thenReturn(dto);

        ResponseEntity<ScenarioDto> response = scenarioController.getScenarioById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testGetScenarioInProgress() {
        List<ScenarioDto> scenarios = List.of(
                new ScenarioDto(1L, 1L, List.of("Hero1"), 0, 0, List.of(), null)
        );

        when(scenarioService.getScenarioInProgress(mockRequest)).thenReturn(scenarios);

        ResponseEntity<List<ScenarioDto>> response = scenarioController.getScenarioInProgress(mockRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(scenarios, response.getBody());
    }
}
