package com.anonymousibex.Agents.of.Revature.service;

import com.anonymousibex.Agents.of.Revature.dto.ContinueScenarioRequest;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioDto;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioRequestDto;
import com.anonymousibex.Agents.of.Revature.model.*;
import com.anonymousibex.Agents.of.Revature.repository.*;
import com.anonymousibex.Agents.of.Revature.util.ScenarioMapper;
import com.anonymousibex.Agents.of.Revature.util.ScenarioUtils;
import com.google.genai.Client;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final CalamityRepository calamityRepository;
    private final UserRepository userRepository;
    private final ScenarioRepository scenarioRepository;
    private final UserSelectionRepository userSelectionRepository;
    private final StoryPointOptionRepository storyPointOptionRepository;
    private final GeminiService geminiService;

    private final Function<String, String> callGemini = prompt ->
            new Client()
                    .models
                    .generateContent("gemini-2.0-flash-001", prompt, null)
                    .text();

    public ScenarioDto startScenario(ScenarioRequestDto request){
        var calamity = calamityRepository.findById(request.calamityId())
                .orElseThrow(() -> new EntityNotFoundException("Calamity not found"));
        var user    = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var scenario = new Scenario();
        scenario.setCalamity(calamity);
        scenario.setUser(user);
        scenario.setHero1(request.hero1());
        scenario.setHero2(request.hero2());
        scenario.setHero3(request.hero3());
        scenario.setChapterCount(1);
        scenario.setPointTotal(0);
        scenarioRepository.save(scenario);

        var selections = userSelectionRepository.findByScenarioIdOrderByChapterNumberAsc(scenario.getId());
        var prompt     = ScenarioUtils.buildPrompt(request, scenario, selections);

        // Retry‐wrapped call
        String raw = geminiService.getValidResponse(
                prompt,
                ScenarioUtils::isValidGeminiResponse,
                callGemini
        );

        var firstPoint = ScenarioUtils.parseStoryPoint(raw, scenario, 1);
        scenario.getStoryPoints().add(firstPoint);
        scenarioRepository.save(scenario);

        return ScenarioMapper.toDto(scenario);
    }

    public ScenarioDto continueScenario(Long scenarioId, ContinueScenarioRequest req) {
        var scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new EntityNotFoundException("Scenario not found"));

        var picked = storyPointOptionRepository.findById(req.selectedOptionId())
                .orElseThrow(() -> new EntityNotFoundException("Option not found"));

        // Record user selection
        int currentChapter = scenario.getChapterCount();
        var sel = new UserSelection();
        sel.setScenario(scenario);
        sel.setStoryPoint(picked.getStoryPoint());
        sel.setSelectedOption(picked);
        sel.setChapterNumber(currentChapter);
        userSelectionRepository.save(sel);

        // Update scenario totals
        scenario.setPointTotal(scenario.getPointTotal() + picked.getPoints());
        scenario.setChapterCount(currentChapter + 1);
        scenarioRepository.save(scenario);

        int nextChapter = scenario.getChapterCount();

        if (nextChapter <= 5) {
            // Normal chapter 2–5 flow
            var allSelections = userSelectionRepository
                    .findByScenarioIdOrderByChapterNumberAsc(scenarioId);
            var prompt = ScenarioUtils.buildPrompt(
                    ScenarioUtils.toRequestDto(scenario),
                    scenario,
                    allSelections
            );

            String raw = geminiService.getValidResponse(
                    prompt,
                    ScenarioUtils::isValidGeminiResponse,
                    callGemini
            );

            var nextPoint = ScenarioUtils.parseStoryPoint(raw, scenario, nextChapter);
            scenario.getStoryPoints().add(nextPoint);
            scenarioRepository.save(scenario);

            return ScenarioMapper.toDto(scenario);

        } else {
            // Final chapter 5 wrap‑up
            var recap = ScenarioUtils.buildContextRecap(
                    scenario,
                    userSelectionRepository.findByScenarioIdOrderByChapterNumberAsc(scenarioId)
            );
            var prompt = ScenarioUtils.FINAL_PROMPT +
                    "\n\nContext recap:\n" + recap;

            // For final, we don’t need delimiter validation
            String closingNarrative = callGemini.apply(prompt).trim();

            scenario.setComplete(true);
            scenario.setClosing(closingNarrative);
            scenarioRepository.save(scenario);

            return ScenarioMapper.toDto(scenario);
        }
    }
}
