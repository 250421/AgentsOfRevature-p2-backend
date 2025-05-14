package com.anonymousibex.Agents.of.Revature.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anonymousibex.Agents.of.Revature.dto.CalamitySelectionsDto;
import com.anonymousibex.Agents.of.Revature.dto.ResultsDto;
import com.anonymousibex.Agents.of.Revature.model.Results;
import com.anonymousibex.Agents.of.Revature.model.Selections;
import com.anonymousibex.Agents.of.Revature.service.ResultsService;
import com.anonymousibex.Agents.of.Revature.service.SelectionsService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/results")
public class ResultsController {
    
    private final ResultsService resultsService;
    private final SelectionsService selectionsService;

    @GetMapping("/leaderboard")
    public ResponseEntity<List<ResultsDto>> getAll(HttpServletRequest request){
        List<Results> results = resultsService.getAllResults();
        List <ResultsDto> response = results.stream()
        .map(element -> {
            return new ResultsDto(
                element.getUser().getId(),
                element.getUser().getUsername(),
                element.getCalamity().getId(),
                element.isDidWin(),
                element.getRepGained()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.status(200).body(response);

    }
    
    @GetMapping("/userResults/{id}")
    public ResponseEntity<List<ResultsDto>> getUserResults(@PathVariable Long id){
        List<Results> results = resultsService.getAllUserResults(id);
         List <ResultsDto> response = results.stream()
        .map(element -> {
            return new ResultsDto(
                element.getUser().getId(),
                element.getUser().getUsername(),
                element.getCalamity().getId(),
                element.isDidWin(),
                element.getRepGained()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.status(200).body(response);

    }

//    @PostMapping("/addResult")
//    public ResponseEntity<ResultsDto> AddResult(@RequestBody Results result) {
//        Results addedResult = resultsService.AddResult(result);
//        ResultsDto response = new ResultsDto(addedResult.getUser_id(),addedResult.getUser().getUsername(), addedResult.getCalamity_id(), addedResult.isDidWin(), addedResult.getRepGained());
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

    @GetMapping("/calamitySelections/{id}")
    public ResponseEntity<CalamitySelectionsDto> getCalamitySelections(@PathVariable Long id){
        Selections calamitySelections = selectionsService.getCalamitySelections(id);
         CalamitySelectionsDto response = new CalamitySelectionsDto(calamitySelections.getUser_id(), calamitySelections.getUser().getUsername(),calamitySelections.getCalamity_id(), calamitySelections.getHero1(), calamitySelections.getHero2(), calamitySelections.getHero3());
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/addSelection")
    public ResponseEntity<CalamitySelectionsDto> AddSelection(@RequestBody Selections selection) {
        Selections addedResult = selectionsService.addUserSelections(selection);   
        CalamitySelectionsDto response = new CalamitySelectionsDto(addedResult.getUser_id(), addedResult.getUser().getUsername(),addedResult.getCalamity_id(), addedResult.getHero1(), addedResult.getHero2(), addedResult.getHero3());
        return ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/updateResult")
    public ResponseEntity<Results> updateResult(@RequestBody Results result) {
        Results updatedResult = resultsService.UpdateResult(result);   
        return ResponseEntity.status(200).body(updatedResult);
    }
    
}
