package com.anonymousibex.Agents.of.Revature.service;

import com.anonymousibex.Agents.of.Revature.dto.ScenarioDto;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioRequestDto;
import com.anonymousibex.Agents.of.Revature.model.*;
import com.anonymousibex.Agents.of.Revature.repository.CalamityRepository;
import com.anonymousibex.Agents.of.Revature.repository.ScenarioRepository;
import com.anonymousibex.Agents.of.Revature.repository.UserRepository;
import com.anonymousibex.Agents.of.Revature.repository.UserSelectionRepository;
import com.anonymousibex.Agents.of.Revature.util.ScenarioMapper;
import com.anonymousibex.Agents.of.Revature.util.ScenarioUtils;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
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

}
