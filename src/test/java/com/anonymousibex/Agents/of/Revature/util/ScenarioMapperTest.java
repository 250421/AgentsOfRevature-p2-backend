package com.anonymousibex.Agents.of.Revature.util;

import com.anonymousibex.Agents.of.Revature.dto.ScenarioDto;
import com.anonymousibex.Agents.of.Revature.dto.StoryPointDto;
import com.anonymousibex.Agents.of.Revature.dto.StoryPointOptionDto;
import com.anonymousibex.Agents.of.Revature.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ScenarioMapperTest {

    @Test
    void toDto_storyPoint_convertsAllFields() {
        // arrange
        StoryPointOption opt1 = new StoryPointOption();
        opt1.setId(10L);
        opt1.setText("Option A");
        StoryPointOption opt2 = new StoryPointOption();
        opt2.setId(11L);
        opt2.setText("Option B");

        StoryPoint sp = new StoryPoint();
        sp.setId(5L);
        sp.setText("A dramatic moment");
        sp.setChapterNumber(3);
        sp.setOptions(List.of(opt1, opt2));
        // back‑pointer
        opt1.setStoryPoint(sp);
        opt2.setStoryPoint(sp);

        // act
        StoryPointDto dto = ScenarioMapper.toDto(sp);

        // assert
        assertThat(dto.id()).isEqualTo(5L);
        assertThat(dto.text()).isEqualTo("A dramatic moment");
        assertThat(dto.chapterNumber()).isEqualTo(3);

        List<StoryPointOptionDto> opts = dto.options();
        assertThat(opts).hasSize(2);
        assertThat(opts).extracting(StoryPointOptionDto::id)
                .containsExactly(10L, 11L);
        assertThat(opts).extracting(StoryPointOptionDto::text)
                .containsExactly("Option A", "Option B");
    }

    @Test
    void toDto_scenario_includesAllFieldsAndNestedStoryPoints() {
        // arrange calamity
        Calamity calamity = new Calamity();
        calamity.setId(99L);
        // hero selection
        HeroSelection hs = new HeroSelection();
        hs.setHero1("H1"); hs.setHero2("H2"); hs.setHero3("H3");

        // story point #1
        StoryPointOption o1 = new StoryPointOption();
        o1.setId(21L);
        o1.setText("Go left");
        StoryPoint sp1 = new StoryPoint();
        sp1.setId(101L);
        sp1.setText("You arrive at a fork.");
        sp1.setChapterNumber(1);
        sp1.setOptions(List.of(o1));
        o1.setStoryPoint(sp1);

        // story point #2
        StoryPointOption o2 = new StoryPointOption();
        o2.setId(22L);
        o2.setText("Go right");
        StoryPoint sp2 = new StoryPoint();
        sp2.setId(102L);
        sp2.setText("You see a river.");
        sp2.setChapterNumber(2);
        sp2.setOptions(List.of(o2));
        o2.setStoryPoint(sp2);

        Scenario scenario = new Scenario();
        scenario.setId(500L);
        scenario.setCalamity(calamity);
        scenario.setHeroSelection(hs);
        scenario.setPointTotal(7);
        scenario.setChapterCount(2);
        scenario.setClosing("And they lived happily.");
        scenario.setStoryPoints(List.of(sp1, sp2));

        // act
        ScenarioDto dto = ScenarioMapper.toDto(scenario);

        // assert top‑level
        assertThat(dto.id()).isEqualTo(500L);
        assertThat(dto.calamityId()).isEqualTo(99L);
        assertThat(dto.heroes()).containsExactly("H1","H2","H3");
        assertThat(dto.pointTotal()).isEqualTo(7);
        assertThat(dto.chapterCount()).isEqualTo(2);
        assertThat(dto.closing()).isEqualTo("And they lived happily.");

        // nested storyPoints
        List<StoryPointDto> spDtos = dto.storyPoints();
        assertThat(spDtos).hasSize(2);

        // verify mapping for first
        StoryPointDto dto1 = spDtos.get(0);
        assertThat(dto1.id()).isEqualTo(101L);
        assertThat(dto1.text()).isEqualTo("You arrive at a fork.");
        assertThat(dto1.chapterNumber()).isEqualTo(1);
        assertThat(dto1.options()).singleElement()
                .satisfies(optDto -> {
                    assertThat(optDto.id()).isEqualTo(21L);
                    assertThat(optDto.text()).isEqualTo("Go left");
                });

        // verify mapping for second
        StoryPointDto dto2 = spDtos.get(1);
        assertThat(dto2.id()).isEqualTo(102L);
        assertThat(dto2.text()).isEqualTo("You see a river.");
        assertThat(dto2.chapterNumber()).isEqualTo(2);
        assertThat(dto2.options()).singleElement()
                .satisfies(optDto -> {
                    assertThat(optDto.id()).isEqualTo(22L);
                    assertThat(optDto.text()).isEqualTo("Go right");
                });
    }
}
