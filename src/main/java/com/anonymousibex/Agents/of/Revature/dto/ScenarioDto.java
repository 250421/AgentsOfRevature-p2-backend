package com.anonymousibex.Agents.of.Revature.dto;

import java.util.List;

public record ScenarioDto(
        Long id,
        Long calamityId,
        List<String> heroes,
        int pointTotal,
        int chapterCount,
        List<StoryPointDto> storyPoints
) {}