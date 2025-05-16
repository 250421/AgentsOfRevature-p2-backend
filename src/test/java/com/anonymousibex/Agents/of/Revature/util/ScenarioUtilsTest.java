package com.anonymousibex.Agents.of.Revature.util;

import com.anonymousibex.Agents.of.Revature.dto.ScenarioRequestDto;
import com.anonymousibex.Agents.of.Revature.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ScenarioUtilsTest {

    @Test
    void isValidGeminiResponse_trueWhenExactlySevenTokens() {
        String good = "A>B>1>C>2>D>3";
        assertThat(ScenarioUtils.isValidGeminiResponse(good)).isTrue();
    }

    @Test
    void isValidGeminiResponse_falseOtherwise() {
        // too few
        assertThat(ScenarioUtils.isValidGeminiResponse("A>B>1>C>2")).isFalse();
        // too many
        assertThat(ScenarioUtils.isValidGeminiResponse("A>B>1>C>2>D>3>E>4")).isFalse();
    }

    @Test
    void thresholds_mapContainsAllSeverities() {
        Map<Severity,Integer> m = ScenarioUtils.THRESHOLDS;
        assertThat(m).containsEntry(Severity.CRITICAL, 14)
                .containsEntry(Severity.HIGH,     12)
                .containsEntry(Severity.MEDIUM,   10)
                .containsEntry(Severity.LOW,       8);
    }

    @Test
    void toRequestDto_pullsHeroesFromScenario() {
        Calamity cal = new Calamity();
        cal.setId(123L);
        HeroSelection hs = new HeroSelection();
        hs.setHero1("X"); hs.setHero2("Y"); hs.setHero3("Z");
        Scenario s = new Scenario();
        s.setCalamity(cal);
        s.setHeroSelection(hs);

        ScenarioRequestDto dto = ScenarioUtils.toRequestDto(s);

        assertThat(dto.calamityId()).isEqualTo(123L);
        assertThat(dto.hero1()).isEqualTo("X");
        assertThat(dto.hero2()).isEqualTo("Y");
        assertThat(dto.hero3()).isEqualTo("Z");
    }

    @Test
    void buildContextRecap_formatsSelectionsInOrder() {
        Scenario s = new Scenario();

        StoryPoint p1 = new StoryPoint();
        p1.setText("First");
        StoryPointOption o1 = new StoryPointOption("optA",1);
        o1.setStoryPoint(p1);
        StoryPointSelection sel1 = new StoryPointSelection();
        sel1.setChapterNumber(2);
        sel1.setStoryPoint(p1);
        sel1.setSelectedOption(o1);

        StoryPoint p2 = new StoryPoint();
        p2.setText("Second");
        StoryPointOption o2 = new StoryPointOption("optB",2);
        o2.setStoryPoint(p2);
        StoryPointSelection sel2 = new StoryPointSelection();
        sel2.setChapterNumber(1);
        sel2.setStoryPoint(p2);
        sel2.setSelectedOption(o2);

        // out of order in list:
        String recap = ScenarioUtils.buildContextRecap(s, List.of(sel1, sel2));

        // buildContextRecap does not sort, it uses given order:
        assertThat(recap)
                .startsWith("Chapter 2: First My choice: optA. ")
                .endsWith("Chapter 1: Second My choice: optB. ");
    }

    @Test
    void parseStoryPoint_happyPath_createsPointAndOptions() {
        Scenario scenario = new Scenario();
        String raw = "Story here>Choice1>0>Choice2>1>Choice3>2";
        StoryPoint point = ScenarioUtils.parseStoryPoint(raw, scenario, 42);

        assertThat(point.getScenario()).isSameAs(scenario);
        assertThat(point.getText()).isEqualTo("Story here");
        assertThat(point.getChapterNumber()).isEqualTo(42);

        List<StoryPointOption> opts = point.getOptions();
        assertThat(opts).hasSize(3);
        assertThat(opts.get(0).getText()).isEqualTo("Choice1");
        assertThat(opts.get(0).getPoints()).isEqualTo(0);
        assertThat(opts.get(1).getText()).isEqualTo("Choice2");
        assertThat(opts.get(1).getPoints()).isEqualTo(1);
        assertThat(opts.get(2).getText()).isEqualTo("Choice3");
        assertThat(opts.get(2).getPoints()).isEqualTo(2);
        // check back‑pointer
        assertThat(opts).allSatisfy(opt -> assertThat(opt.getStoryPoint()).isSameAs(point));
    }

    @Test
    void parseStoryPoint_throwsWhenBadDelimiterCount() {
        Scenario scenario = new Scenario();
        String raw = "Too few>Only one>0>Second>1"; // only 2 options
        assertThatThrownBy(() ->
                ScenarioUtils.parseStoryPoint(raw, scenario, 1)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Expected exactly 3 options but found 2");
    }

    @Test
    void parseStoryPoint_throwsWhenNonNumericPoints() {
        Scenario scenario = new Scenario();
        String raw = "Story>OptA>notANumber>OptB>1>OptC>2";
        assertThatThrownBy(() ->
                ScenarioUtils.parseStoryPoint(raw, scenario, 1)
        ).isInstanceOf(NumberFormatException.class)
                .hasMessageContaining("For input string");
    }

    @Test
    void buildPrompt_withoutSelections_includesFirstChapterAndNoPriorContext() {
        ScenarioRequestDto dto = new ScenarioRequestDto(7L, "H1","H2","H3");
        Calamity cal = new Calamity();
        cal.setTitle("CTitle");
        cal.setDescription("CDesc");
        cal.setVillain("Dr Evil");
        cal.setSeverity(Severity.HIGH);
        Scenario s = new Scenario();
        s.setCalamity(cal);
        s.setHeroSelection(new HeroSelection() {{ setHero1("H1");setHero2("H2");setHero3("H3"); }});
        s.setChapterCount(3);
        s.setPointTotal(5);

        String prompt = ScenarioUtils.buildPrompt(dto, s, List.of());

        assertThat(prompt).contains("Mission team: H1, H2, H3.");
        assertThat(prompt).contains("Calamity: CTitle — CDesc -- Villain responsible: Dr Evil (Severity: HIGH)");
        assertThat(prompt).contains("This is Chapter 3 of 5.");
        assertThat(prompt).contains("My current point total is: 5.");
        assertThat(prompt).contains("first chapter — no prior events");
    }

    @Test
    void buildPrompt_withSelections_appendsOrderedHistory() {
        ScenarioRequestDto dto = new ScenarioRequestDto(1L, "A","B","C");
        Calamity cal = new Calamity();
        cal.setVillain("X");
        Scenario s = new Scenario();
        s.setCalamity(cal);
        s.setHeroSelection(new HeroSelection() {{ setHero1("A");setHero2("B");setHero3("C"); }});
        s.setChapterCount(2);
        s.setPointTotal(3);

        StoryPointSelection sel = new StoryPointSelection();
        StoryPoint sp = new StoryPoint(); sp.setText("Once");
        StoryPointOption spo = new StoryPointOption("You chose me",1);
        spo.setStoryPoint(sp);
        sel.setChapterNumber(1);
        sel.setStoryPoint(sp);
        sel.setSelectedOption(spo);

        String prompt = ScenarioUtils.buildPrompt(dto, s, List.of(sel));

        assertThat(prompt).contains("Chapter 1: Once");
        assertThat(prompt).contains("My choice: You chose me");
    }
}
