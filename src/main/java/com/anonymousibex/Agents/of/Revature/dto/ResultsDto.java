package com.anonymousibex.Agents.of.Revature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResultsDto {
    private Long id;
    private Long userId;
    private String username;
    private Long calamityId;
    private boolean didWin;
    private int repGained;
    private List<String> heroes;
}
