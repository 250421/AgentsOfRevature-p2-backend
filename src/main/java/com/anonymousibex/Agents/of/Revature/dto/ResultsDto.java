package com.anonymousibex.Agents.of.Revature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResultsDto {
    private Long userId;
    private String username;
    private Long calamity_id;
    private boolean didWin;
    private int repGained;
}
