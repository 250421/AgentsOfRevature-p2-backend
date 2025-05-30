package com.anonymousibex.Agents.of.Revature.controller;

import com.anonymousibex.Agents.of.Revature.dto.ContinueScenarioRequest;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioDto;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioRequestDto;
import com.anonymousibex.Agents.of.Revature.model.Scenario;
import com.anonymousibex.Agents.of.Revature.service.ScenarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scenario")
public class ScenarioController {

    private final ScenarioService scenarioService;

    @PostMapping
    public ResponseEntity<ScenarioDto> startScenario(@RequestBody ScenarioRequestDto requestDto, HttpServletRequest httpRequest) {
        ScenarioDto scenarioDto = scenarioService.startScenario(requestDto, httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(scenarioDto);
    }

    @PatchMapping("/{scenarioId}")
    public ResponseEntity<ScenarioDto> continueScenario(
            @PathVariable Long scenarioId,
            @RequestBody ContinueScenarioRequest req
    ) {
        ScenarioDto updated = scenarioService.continueScenario(scenarioId, req);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{scenarioId}")
    public ResponseEntity<ScenarioDto> getScenarioById(@PathVariable Long scenarioId){
        ScenarioDto scenario = scenarioService.getScenarioById(scenarioId);
        return ResponseEntity.ok(scenario);
    }

    @GetMapping("/in-progress")
    public ResponseEntity<List<ScenarioDto>> getScenarioInProgress(HttpServletRequest request){
        List<ScenarioDto> scenarios = scenarioService.getScenarioInProgress(request);
        return ResponseEntity.ok(scenarios);
    }
}
