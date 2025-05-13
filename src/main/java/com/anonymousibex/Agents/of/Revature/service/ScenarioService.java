package com.anonymousibex.Agents.of.Revature.service;

import com.anonymousibex.Agents.of.Revature.dto.ContinueScenarioRequest;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioDto;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioRequestDto;
import com.anonymousibex.Agents.of.Revature.model.*;
import com.anonymousibex.Agents.of.Revature.repository.*;
import com.anonymousibex.Agents.of.Revature.util.ScenarioMapper;
import com.anonymousibex.Agents.of.Revature.util.ScenarioUtils;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final CalamityRepository calamityRepository;
    private final UserRepository userRepository;
    private final ScenarioRepository scenarioRepository;
    private final UserSelectionRepository userSelectionRepository;
    private final StoryPointOptionRepository storyPointOptionRepository;

    public ScenarioDto startScenario(ScenarioRequestDto request){
        Client client = new Client();
        Calamity calamity = calamityRepository.findById(request.calamityId()).orElseThrow();
        User user = userRepository.findById(request.userId()).orElseThrow();

        Scenario scenario = new Scenario();
        scenario.setCalamity(calamity);
        scenario.setUser(user);
        scenario.setHero1(request.hero1());
        scenario.setHero2(request.hero2());
        scenario.setHero3(request.hero3());
        scenario.setChapterCount(1);
        scenario.setPointTotal(0);

        scenarioRepository.save(scenario); // save before querying selections

        List<UserSelection> selections = userSelectionRepository
                .findByScenarioIdOrderByChapterNumberAsc(scenario.getId());
        String prompt = ScenarioUtils.buildPrompt(request, scenario, selections);

        GenerateContentResponse rawResponse =
                client.models.generateContent("gemini-2.0-flash-001", prompt, null);
        String response = rawResponse.text();

        StoryPoint point = ScenarioUtils.parseStoryPoint(response, scenario, 1);
        scenario.getStoryPoints().add(point);
        scenarioRepository.save(scenario);
        return ScenarioMapper.toDto(scenario);
    }

    public ScenarioDto continueScenario(Long scenarioId, ContinueScenarioRequest req) {
        // 1) Load scenario
        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new EntityNotFoundException("Scenario not found"));

        // 2) Persist the user’s selection for the previous chapter
        StoryPointOption picked = storyPointOptionRepository.findById(req.selectedOptionId())
                .orElseThrow(() -> new EntityNotFoundException("Option not found"));

        int currentChapter = scenario.getChapterCount(); // e.g. 1 for first continuation
        UserSelection sel = new UserSelection();
        sel.setScenario(scenario);
        sel.setStoryPoint(picked.getStoryPoint());
        sel.setSelectedOption(picked);
        sel.setChapterNumber(currentChapter);
        userSelectionRepository.save(sel);

        // 3) Update points & chapterCount
        scenario.setPointTotal(scenario.getPointTotal() + picked.getPoints());
        scenario.setChapterCount(currentChapter + 1);
        scenarioRepository.save(scenario);

        // 4) Build the prompt with full history
        List<UserSelection> allSelections = userSelectionRepository
                .findByScenarioIdOrderByChapterNumberAsc(scenarioId);
        String prompt = ScenarioUtils.buildPrompt(
                ScenarioUtils.toRequestDto(scenario), // helper to convert back if needed
                scenario,
                allSelections
        );

        // 5) Call Gemini for the next chapter
        GenerateContentResponse raw = new Client()
                .models.generateContent("gemini-2.0-flash-001", prompt, null);

        // 6) Parse and save the next StoryPoint
        StoryPoint nextPoint = ScenarioUtils.parseStoryPoint(
                raw.text(),
                scenario,
                scenario.getChapterCount()
        );
        scenario.getStoryPoints().add(nextPoint);
        scenarioRepository.save(scenario);

        // 7) Return DTO
        return ScenarioMapper.toDto(scenario);
    }

}
