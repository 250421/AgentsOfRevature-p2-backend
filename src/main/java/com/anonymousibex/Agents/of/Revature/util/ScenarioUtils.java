package com.anonymousibex.Agents.of.Revature.util;

import com.anonymousibex.Agents.of.Revature.dto.ScenarioDto;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioRequestDto;
import com.anonymousibex.Agents.of.Revature.dto.StoryPointDto;
import com.anonymousibex.Agents.of.Revature.model.Scenario;
import com.anonymousibex.Agents.of.Revature.model.StoryPoint;
import com.anonymousibex.Agents.of.Revature.model.StoryPointOption;
import com.anonymousibex.Agents.of.Revature.model.UserSelection;

import java.util.Comparator;
import java.util.List;

public class ScenarioUtils {
    static final public String PROMPT = "I am an agent who works directly with a stable of heros and superheros. I respond to missions, called calamities, by selecting three heros to bring with me to save the day. There is always a villain behind the calamity, but I don't know who it is until I arrive at the scene. Your job will be to recieve the mission details, the heros I brought with me, the severity of the calamity, and the chapter of the adventure, and then you will generate a text adventure where I will get to choose from 3 options. Each text adventure will be 5 chapters long. Any chapters that have already occurred will be provided for context. The villain will always be revealed in chapter 1. The difficulty in succeeding in saving the day should depend on the calamity severity (critical, high, medium, low). The difficuly will not change as the chapters progress. After my selection on chapter 5, the final chapter of the scenario, you need to generate a closing scenario and tell me if I've succeeded or failed. You should also provide the point values for each choice. These will be obscured from the user, but I need them to give back to you for each successive chapter so you know the running point total. A bonus point should be awarded if I effectively utilize a hero from the same universe as the villain, and the most points I can win in a round is 3, including the bonus point. For critical missions, I'll need 14 points to succeed (maximum of 15 total). For high severity missions I'll need 12, for medium I'll need 10, and for low I'll need 8. You need to return the scenario and my options to me in the exact format provided with no additional text, styling, or formatting: This is an example scenario.<>This is the first option.<>2<>This is the second option.<>1<>This is the third and final option for this scenario.<>1<> The context is as follows:";

    public static String buildPrompt(ScenarioRequestDto dto, Scenario scenario, List<UserSelection> selections) {
        StringBuilder promptBuilder = new StringBuilder(PROMPT);

        // Hero team
        promptBuilder.append("\nMission team: ")
                .append(dto.hero1()).append(", ")
                .append(dto.hero2()).append(", ")
                .append(dto.hero3()).append(".");

        // Calamity description & severity
        promptBuilder.append("\nCalamity: ")
                .append(scenario.getCalamity().getTitle())
                .append(" — ").append(scenario.getCalamity().getDescription())
                .append(" (Severity: ").append(scenario.getCalamity().getSeverity()).append(")");

        // Chapter info
        promptBuilder.append("\nThis is Chapter ")
                .append(scenario.getChapterCount())
                .append(" of 5.");

        // Running point total
        promptBuilder.append("\nMy current point total is: ")
                .append(scenario.getPointTotal())
                .append(".");

        // Add previous story context if it exists
        if (selections != null && !selections.isEmpty()) {
            promptBuilder.append("\n\nThe context is as follows:\n");
            selections.stream()
                    .sorted(Comparator.comparingInt(UserSelection::getChapterNumber))
                    .forEach(selection -> {
                        StoryPoint point = selection.getStoryPoint();
                        promptBuilder.append("Chapter ").append(selection.getChapterNumber()).append(": ")
                                .append(point.getText()).append("\n")
                                .append("My choice: ").append(selection.getSelectedOption().getText()).append("\n");
                    });
        } else {
            promptBuilder.append("\n\nThe context is as follows: (first chapter — no prior events)");
        }

        return promptBuilder.toString();
    }

    public static StoryPoint parseStoryPoint(String raw, Scenario scenario, int chapterNum) {
        String[] parts = raw.split("<>");
        StoryPoint point = new StoryPoint();
        point.setScenario(scenario);
        point.setText(parts[0]);

        List<StoryPointOption> options = List.of(
                new StoryPointOption(parts[1], Integer.parseInt(parts[2])),
                new StoryPointOption(parts[3], Integer.parseInt(parts[4])),
                new StoryPointOption(parts[5], Integer.parseInt(parts[6]))
        );
        options.forEach(opt -> opt.setStoryPoint(point));

        point.setOptions(options);
        point.setChapterNumber(chapterNum);
        return point;
    }

}