package com.anonymousibex.Agents.of.Revature.service;

import com.anonymousibex.Agents.of.Revature.dto.ContinueScenarioRequest;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioDto;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioRequestDto;
import com.anonymousibex.Agents.of.Revature.exception.CalamityNotFoundException;
import com.anonymousibex.Agents.of.Revature.exception.ScenarioNotFoundException;
import com.anonymousibex.Agents.of.Revature.model.*;
import com.anonymousibex.Agents.of.Revature.repository.*;
import com.anonymousibex.Agents.of.Revature.util.ScenarioUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class ScenarioServiceTest {

    @Mock CalamityRepository calamityRepo;
    @Mock ScenarioRepository scenarioRepo;
    @Mock StoryPointSelectionRepository selectionRepo;
    @Mock StoryPointOptionRepository optionRepo;
    @Mock GeminiService geminiService;
    @Mock ResultsService resultsService;
    @Mock UserService userService;
    @Mock HttpServletRequest httpRequest;

    @InjectMocks ScenarioService service;

    @BeforeEach
    void setUp() {
        // ensure any call to getValidResponse matches loosely
        // no-op here; we'll use any() matchers in each stub
    }

    @Test
    void startScenario_happyPath_createsFirstPointAndReturnsDto() {
        // given
        ScenarioRequestDto dto = new ScenarioRequestDto(42L, "HeroA","HeroB","HeroC");
        Calamity calamity = new Calamity();
        calamity.setId(42L);
        calamity.setTitle("Test Calamity");
        calamity.setDescription("Desc");
        calamity.setSeverity(Severity.MEDIUM);

        User user = new User();
        user.setId(99L);

        given(calamityRepo.findById(42L)).willReturn(Optional.of(calamity));
        given(userService.getCurrentUserBySession(httpRequest)).willReturn(user);

        // assign an ID on first save
        willAnswer(inv -> {
            Scenario s = inv.getArgument(0);
            s.setId(100L);
            return s;
        }).given(scenarioRepo).save(any(Scenario.class));

        given(selectionRepo.findByScenarioIdOrderByChapterNumberAsc(100L))
                .willReturn(Collections.emptyList());

        // stub Gemini loosely
        String raw = "Once upon a test>Opt1>1>Opt2>2>Opt3>1";
        given(geminiService.getValidResponse(
                anyString(),
                any(),    // loosened
                any()))
                .willReturn(raw);

        // when
        ScenarioDto result = service.startScenario(dto, httpRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(100L);
        assertThat(result.calamityId()).isEqualTo(42L);
        assertThat(result.heroes()).containsExactly("HeroA","HeroB","HeroC");
        assertThat(result.pointTotal()).isZero();
        assertThat(result.chapterCount()).isEqualTo(1);
        assertThat(result.storyPoints()).hasSize(1);

        then(geminiService).should(times(1))
                .getValidResponse(anyString(), any(), any());
    }

    @Test
    void continueScenario_nextChapterlte5_appendsPointAndReturnsDto() {
        // given an existing scenario at chapterCount=1 with id=200
        Scenario scenario = new Scenario();
        scenario.setId(200L);
        scenario.setChapterCount(1);
        scenario.setPointTotal(0);
        Calamity calamity = new Calamity();
        calamity.setSeverity(Severity.LOW);
        scenario.setCalamity(calamity);

        // **attach a HeroSelection** so toRequestDto() won't NPE
        HeroSelection hs = new HeroSelection();
        hs.setHero1("HeroA");
        hs.setHero2("HeroB");
        hs.setHero3("HeroC");
        scenario.setHeroSelection(hs);

        given(scenarioRepo.findById(200L)).willReturn(Optional.of(scenario));

        // user picks an option
        StoryPoint dummyPoint = new StoryPoint();
        dummyPoint.setScenario(scenario);
        StoryPointOption picked = new StoryPointOption("ChoiceX", 3);
        picked.setStoryPoint(dummyPoint);
        picked.setId(555L);
        given(optionRepo.findById(555L)).willReturn(Optional.of(picked));

        given(selectionRepo.findByScenarioIdOrderByChapterNumberAsc(200L))
                .willReturn(Collections.emptyList());

        String raw2 = "Chapter2 text>O1>0>O2>1>O3>2";
        given(geminiService.getValidResponse(
                anyString(),
                any(),
                any()))
                .willReturn(raw2);

        // when
        ContinueScenarioRequest req = new ContinueScenarioRequest(555L);
        ScenarioDto dto = service.continueScenario(200L, req);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.pointTotal()).isEqualTo(3);
        assertThat(dto.chapterCount()).isEqualTo(2);
        assertThat(dto.storyPoints()).hasSize(1);

        then(selectionRepo).should().save(any(StoryPointSelection.class));
        then(scenarioRepo).should(atLeastOnce()).save(scenario);
        then(geminiService).should().getValidResponse(anyString(), any(), any());
    }

    @Test
    void startScenario_missingCalamity_throws() {
        given(calamityRepo.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() ->
                service.startScenario(new ScenarioRequestDto(1L,"x","y","z"), httpRequest)
        ).isInstanceOf(CalamityNotFoundException.class);
    }

    @Test
    void continueScenario_unknownScenario_throws() {
        given(scenarioRepo.findById(123L)).willReturn(Optional.empty());
        assertThatThrownBy(() ->
                service.continueScenario(123L, new ContinueScenarioRequest(5L))
        ).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void continueScenario_pastChapter5_savesResultAndClosing() {
        // given a scenario already at chapterCount = 5
        Scenario scenario = new Scenario();
        scenario.setId(300L);
        scenario.setChapterCount(5);
        scenario.setPointTotal(10);
        Calamity calamity = new Calamity();
        calamity.setSeverity(Severity.LOW);
        scenario.setCalamity(calamity);
        HeroSelection hs = new HeroSelection();
        hs.setHero1("A"); hs.setHero2("B"); hs.setHero3("C");
        scenario.setHeroSelection(hs);
        given(scenarioRepo.findById(300L)).willReturn(Optional.of(scenario));

        // pick option of 1 pt so total=11 → nextChapter=6
        StoryPoint dummy = new StoryPoint();
        dummy.setScenario(scenario);
        StoryPointOption picked = new StoryPointOption("X",1);
        picked.setStoryPoint(dummy);
        picked.setId(999L);
        given(optionRepo.findById(999L)).willReturn(Optional.of(picked));

        // for final prompt, just return a canned closing
        given(geminiService.getValidResponse(anyString(), any(), any()))
                .willReturn("🏁 The heroes wrap up.");

        // when
        ScenarioDto dto = service.continueScenario(300L, new ContinueScenarioRequest(999L));

        // then
        assertThat(dto.chapterCount()).isEqualTo(6);
        assertThat(dto.closing()).isEqualTo("🏁 The heroes wrap up.");

        // verify we recorded a result
        then(resultsService).should().addResult(eq(scenario), eq(true), eq(11));
    }

    @Test
    void getScenarioById_found() {
        Scenario scenario = new Scenario();
        scenario.setId(400L);
        Calamity cal = new Calamity(); cal.setId(7L); cal.setSeverity(Severity.HIGH);
        scenario.setCalamity(cal);
        HeroSelection hs = new HeroSelection(); hs.setHero1("X");hs.setHero2("Y");hs.setHero3("Z");
        scenario.setHeroSelection(hs);
        given(scenarioRepo.findById(400L)).willReturn(Optional.of(scenario));

        ScenarioDto dto = service.getScenarioById(400L);
        assertThat(dto.id()).isEqualTo(400L);
        assertThat(dto.heroes()).containsExactly("X","Y","Z");
    }

    @Test
    void getScenarioById_notFound_throws() {
        given(scenarioRepo.findById(123L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.getScenarioById(123L))
                .isInstanceOf(ScenarioNotFoundException.class);
    }

    @Test
    void getScenarioInProgress_returnsDtos() {
        User user = new User(); user.setId(55L);
        given(userService.getCurrentUserBySession(httpRequest)).willReturn(user);

        Scenario one = new Scenario(); one.setId(1L); one.setComplete(false);
        Calamity c1 = new Calamity(); c1.setId(10L); c1.setSeverity(Severity.LOW);
        one.setCalamity(c1);
        HeroSelection hs = new HeroSelection(); hs.setHero1("H1");hs.setHero2("H2");hs.setHero3("H3");
        one.setHeroSelection(hs);

        given(scenarioRepo.findByUserIdAndCompleteFalse(55L))
                .willReturn(List.of(one));

        List<ScenarioDto> dtos = service.getScenarioInProgress(httpRequest);
        assertThat(dtos).hasSize(1)
                .first().satisfies(d -> {
                    assertThat(d.id()).isEqualTo(1L);
                    assertThat(d.heroes()).containsExactly("H1","H2","H3");
                });
    }

    @Test
    void continueScenario_unknownOption_throws() {
        Scenario s = new Scenario();
        s.setId(500L);
        s.setChapterCount(1);
        s.setCalamity(new Calamity());
        s.setHeroSelection(new HeroSelection());
        given(scenarioRepo.findById(500L)).willReturn(Optional.of(s));
        given(optionRepo.findById(888L)).willReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.continueScenario(500L, new ContinueScenarioRequest(888L))
        ).isInstanceOf(EntityNotFoundException.class);
    }


}
