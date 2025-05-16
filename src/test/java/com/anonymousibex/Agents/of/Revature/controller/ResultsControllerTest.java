package com.anonymousibex.Agents.of.Revature.controller;

import com.anonymousibex.Agents.of.Revature.controller.ResultsController;
import com.anonymousibex.Agents.of.Revature.dto.ResultsDto;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioSelectionDto;
import com.anonymousibex.Agents.of.Revature.model.Calamity;
import com.anonymousibex.Agents.of.Revature.model.HeroSelection;
import com.anonymousibex.Agents.of.Revature.model.Results;
import com.anonymousibex.Agents.of.Revature.model.User;
import com.anonymousibex.Agents.of.Revature.service.HeroSelectionService;
import com.anonymousibex.Agents.of.Revature.service.ResultsService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

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
        User user = new User(); user.setId(1L); user.setUsername("testuser");
        Calamity calamity = new Calamity(); calamity.setId(2L);
        Results result = new Results();
        result.setId(100L); result.setUser(user); result.setCalamity(calamity);
        result.setDidWin(true); result.setRepGained(50);

        when(resultsService.getAllResults()).thenReturn(List.of(result));

        ResponseEntity<List<ResultsDto>> response = resultsController.getAll(mock(HttpServletRequest.class));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getUserId());
    }

    @Test
    void getUserResults_shouldReturnUserResultsDtoList() {
        User user = new User(); user.setId(1L); user.setUsername("agent007");
        Calamity calamity = new Calamity(); calamity.setId(2L);
        Results result = new Results();
        result.setId(101L); result.setUser(user); result.setCalamity(calamity);
        result.setDidWin(false); result.setRepGained(30);

        when(resultsService.getAllUserResults(1L)).thenReturn(List.of(result));

        ResponseEntity<List<ResultsDto>> response = resultsController.getUserResults(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(101L, response.getBody().get(0).getId());
        assertEquals("agent007", response.getBody().get(0).getUsername());
    }

    @Test
    void getCalamitySelections_shouldReturnDto() {
        User user = new User(); user.setId(3L); user.setUsername("heroUser");
        Calamity calamity = new Calamity(); calamity.setId(4L);
        HeroSelection selection = new HeroSelection();
        selection.setUser(user); selection.setCalamity(calamity);
        selection.setHero1("Flash"); selection.setHero2("Wonder Woman"); selection.setHero3("Batman");

        when(heroSelectionService.getCalamitySelections(4L)).thenReturn(selection);

        ResponseEntity<ScenarioSelectionDto> response = resultsController.getCalamitySelections(4L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Flash", response.getBody().getHero1());
    }

    @Test
    void addSelection_shouldReturnSavedDto() {
        User user = new User(); user.setId(5L); user.setUsername("addHero");
        Calamity calamity = new Calamity(); calamity.setId(6L);
        HeroSelection selection = new HeroSelection();
        selection.setUser(user); selection.setCalamity(calamity);
        selection.setHero1("Iron Man"); selection.setHero2("Hulk"); selection.setHero3("Thor");

        when(heroSelectionService.addHeroSelections(any())).thenReturn(selection);

        ResponseEntity<ScenarioSelectionDto> response = resultsController.AddSelection(selection);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("addHero", response.getBody().getUsername());
        assertEquals("Thor", response.getBody().getHero3());
    }

    @Test
    void updateResult_shouldReturnUpdatedResult() {
        Results input = new Results();
        input.setId(77L); input.setDidWin(true); input.setRepGained(100);

        when(resultsService.UpdateResult(any())).thenReturn(input);

        ResponseEntity<Results> response = resultsController.updateResult(input);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isDidWin());
        assertEquals(100, response.getBody().getRepGained());
    }
}
