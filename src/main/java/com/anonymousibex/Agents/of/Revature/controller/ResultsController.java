package com.anonymousibex.Agents.of.Revature.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.anonymousibex.Agents.of.Revature.model.Results;
import com.anonymousibex.Agents.of.Revature.service.ResultsService;

import java.util.List;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/results")
public class ResultsController {
    
    private final ResultsService resultsService;

    @GetMapping("/leaderboard")
    public ResponseEntity<List<Results>> getAll(HttpServletRequest request){
        return ResponseEntity.status(200).body(resultsService.getAllResults());

    }
    
    @GetMapping("/userResults")
    public ResponseEntity<List<Results>> getUserResults(HttpServletRequest request){
        return ResponseEntity.status(200).body(resultsService.getAllResults());

    }

    @PostMapping("/addResult")
    public Results AddResult(@RequestBody Results result) {
        Results addedResult = resultsService.AddResult(result);   
        return addedResult;
    }
    
}
