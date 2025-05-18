package com.anonymousibex.Agents.of.Revature.controller;

import com.anonymousibex.Agents.of.Revature.controller.ResultsController;
import com.anonymousibex.Agents.of.Revature.dto.ResultsDto;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioSelectionDto;
import com.anonymousibex.Agents.of.Revature.model.Calamity;
import com.anonymousibex.Agents.of.Revature.model.HeroSelection;
import com.anonymousibex.Agents.of.Revature.model.Results;
import com.anonymousibex.Agents.of.Revature.model.Scenario;
import com.anonymousibex.Agents.of.Revature.model.User;
import com.anonymousibex.Agents.of.Revature.service.HeroSelectionService;
import com.anonymousibex.Agents.of.Revature.service.ResultsService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResultsControllerTest {

    @InjectMocks
    private ResultsController resultsController;

    @Mock
    private ResultsService resultsService;

    @Mock
    private HeroSelectionService heroSelectionService;

    @Test
    void getAll_shouldReturnResultsDtoList() {
        // prepare a scenario and its heroSelection
        HeroSelection hs = new HeroSelection();
        hs.setHero1("Flash");
        hs.setHero2("Batman");
        hs.setHero3("Wonder Woman");

        Scenario scenario = new Scenario();
        scenario.setId(10L);
        scenario.setHeroSelection(hs);

        // prepare user & calamity
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Calamity calamity = new Calamity();
        calamity.setId(2L);

        // prepare result
        Results result = new Results();
        result.setId(100L);
        result.setUser(user);
        result.setCalamity(calamity);
        result.setScenario(scenario);
        result.setDidWin(true);
        result.setRepGained(50);

        when(resultsService.getAllResults()).thenReturn(List.of(result));

        ResponseEntity<List<ResultsDto>> response =
                resultsController.getAll(mock(HttpServletRequest.class));

        assertEquals(200, response.getStatusCodeValue());
        List<ResultsDto> body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.size());

        ResultsDto dto = body.get(0);
        assertEquals(100L, dto.getId());
        assertEquals(1L, dto.getUserId());
        assertEquals("testuser", dto.getUsername());
        assertEquals(2L, dto.getCalamityId());
        assertTrue(dto.isDidWin());
        assertEquals(50, dto.getRepGained());
        // new heroSelection field
        assertEquals(List.of("Flash","Batman","Wonder Woman"), dto.getHeroes());
    }

    @Test
    void getUserResults_shouldReturnUserResultsDtoList() {
        HeroSelection hs = new HeroSelection();
        hs.setHero1("Ironman");
        hs.setHero2("Hulk");
        hs.setHero3("Thor");

        Scenario scenario = new Scenario();
        scenario.setId(20L);
        scenario.setHeroSelection(hs);

        User user = new User();
        user.setId(1L);
        user.setUsername("agent007");

        Calamity calamity = new Calamity();
        calamity.setId(3L);

        Results result = new Results();
        result.setId(101L);
        result.setUser(user);
        result.setCalamity(calamity);
        result.setScenario(scenario);
        result.setDidWin(false);
        result.setRepGained(30);

        when(resultsService.getAllUserResults(1L)).thenReturn(List.of(result));

        ResponseEntity<List<ResultsDto>> response = resultsController.getUserResults(1L);

        assertEquals(200, response.getStatusCodeValue());
        ResultsDto dto = response.getBody().get(0);

        assertEquals(101L, dto.getId());
        assertEquals(1L, dto.getUserId());
        assertEquals("agent007", dto.getUsername());
        assertEquals(3L, dto.getCalamityId());
        assertFalse(dto.isDidWin());
        assertEquals(30, dto.getRepGained());
        assertEquals(List.of("Ironman","Hulk","Thor"), dto.getHeroes());
    }

    @Test
    void getCalamitySelections_shouldReturnDto() {
        User user = new User();
        user.setId(3L);
        user.setUsername("heroUser");

        Calamity calamity = new Calamity();
        calamity.setId(4L);

        HeroSelection selection = new HeroSelection();
        selection.setUser(user);
        selection.setCalamity(calamity);
        selection.setHero1("Flash");
        selection.setHero2("Wonder Woman");
        selection.setHero3("Batman");

        when(heroSelectionService.getCalamitySelections(4L))
                .thenReturn(selection);

        ResponseEntity<ScenarioSelectionDto> response =
                resultsController.getCalamitySelections(4L);

        assertEquals(200, response.getStatusCodeValue());
        ScenarioSelectionDto dto = response.getBody();

        assertEquals(3L, dto.getUserId());
        assertEquals("heroUser", dto.getUsername());
        assertEquals(4L, dto.getCalamity_id());
        assertEquals("Flash", dto.getHero1());
        assertEquals("Wonder Woman", dto.getHero2());
        assertEquals("Batman", dto.getHero3());
    }

    @Test
    void addSelection_shouldReturnSavedDto() {
        User user = new User();
        user.setId(5L);
        user.setUsername("addHero");

        Calamity calamity = new Calamity();
        calamity.setId(6L);

        HeroSelection selection = new HeroSelection();
        selection.setUser(user);
        selection.setCalamity(calamity);
        selection.setHero1("Iron Man");
        selection.setHero2("Hulk");
        selection.setHero3("Thor");

        when(heroSelectionService.addHeroSelections(any()))
                .thenReturn(selection);

        ResponseEntity<ScenarioSelectionDto> response =
                resultsController.AddSelection(selection);

        assertEquals(200, response.getStatusCodeValue());
        ScenarioSelectionDto dto = response.getBody();

        assertEquals(5L, dto.getUserId());
        assertEquals("addHero", dto.getUsername());
        assertEquals(6L, dto.getCalamity_id());
        assertEquals("Iron Man", dto.getHero1());
        assertEquals("Hulk", dto.getHero2());
        assertEquals("Thor", dto.getHero3());
    }

    @Test
    void updateResult_shouldReturnUpdatedResult() {
        Results input = new Results();
        input.setId(77L);
        input.setDidWin(true);
        input.setRepGained(100);

        when(resultsService.UpdateResult(any())).thenReturn(input);

        ResponseEntity<Results> response =
                resultsController.updateResult(input);

        assertEquals(200, response.getStatusCodeValue());
        Results body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isDidWin());
        assertEquals(100, body.getRepGained());
    }
}

