package com.anonymousibex.Agents.of.Revature.service;

import com.anonymousibex.Agents.of.Revature.dto.ContinueScenarioRequest;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioDto;
import com.anonymousibex.Agents.of.Revature.dto.ScenarioRequestDto;
import com.anonymousibex.Agents.of.Revature.exception.CalamityNotFoundException;
import com.anonymousibex.Agents.of.Revature.exception.ScenarioNotFoundException;
import com.anonymousibex.Agents.of.Revature.model.*;
import com.anonymousibex.Agents.of.Revature.repository.*;
import com.anonymousibex.Agents.of.Revature.util.ScenarioMapper;
import com.anonymousibex.Agents.of.Revature.util.ScenarioUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
        // Default stub for any chapter-generation call
        when(geminiService.getValidResponse(anyString(), any()))
                .thenReturn("Stub story>Opt1>1>Opt2>2>Opt3>1");
        // Default stub for final closing
        when(geminiService.generate(anyString()))
                .thenReturn("Default closing text");
    }

    @Test
    void startScenario_happyPath_createsFirstPointAndReturnsDto() {
        // given
        ScenarioRequestDto dto = new ScenarioRequestDto(42L, "HeroA","HeroB","HeroC");
        Calamity calamity = new Calamity();
        calamity.setId(42L);
        calamity.setTitle("Calam");
        calamity.setDescription("Desc");
        calamity.setSeverity(Severity.MEDIUM);
        User user = new User();
        user.setId(99L);

        when(calamityRepo.findById(42L)).thenReturn(Optional.of(calamity));
        when(userService.getCurrentUserBySession(httpRequest)).thenReturn(user);

        // assign ID on first save
        doAnswer(inv -> {
            Scenario s = inv.getArgument(0);
            s.setId(100L);
            return s;
        }).when(scenarioRepo).save(any(Scenario.class));

        when(selectionRepo.findByScenarioIdOrderByChapterNumberAsc(100L))
                .thenReturn(Collections.emptyList());

        // override stub for first chapter
        String raw = "Once>O1>1>O2>2>O3>1";
        when(geminiService.getValidResponse(anyString(), any()))
                .thenReturn(raw);

        // when
        ScenarioDto result = service.startScenario(dto, httpRequest);

        // then
        assertThat(result.id()).isEqualTo(100L);
        assertThat(result.calamityId()).isEqualTo(42L);
        assertThat(result.heroes()).containsExactly("HeroA","HeroB","HeroC");
        assertThat(result.pointTotal()).isZero();
        assertThat(result.chapterCount()).isEqualTo(1);
        assertThat(result.storyPoints()).hasSize(1);

        verify(geminiService, times(1)).getValidResponse(anyString(), any());
    }

    @Test
    void startScenario_missingCalamity_throws() {
        when(calamityRepo.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() ->
                service.startScenario(new ScenarioRequestDto(1L,"x","y","z"), httpRequest)
        ).isInstanceOf(CalamityNotFoundException.class);
    }

    @Test
    void continueScenario_nextChapterle5_appendsPointAndReturnsDto() {
        // given existing scenario at chapter 1
        Scenario scenario = new Scenario();
        scenario.setId(200L);
        scenario.setChapterCount(1);
        scenario.setPointTotal(0);
        Calamity cal = new Calamity(); cal.setSeverity(Severity.LOW);
        scenario.setCalamity(cal);
        HeroSelection hs = new HeroSelection();
        hs.setHero1("A"); hs.setHero2("B"); hs.setHero3("C");
        scenario.setHeroSelection(hs);

        when(scenarioRepo.findById(200L)).thenReturn(Optional.of(scenario));

        StoryPoint stubPoint = new StoryPoint();
        stubPoint.setScenario(scenario);
        StoryPointOption picked = new StoryPointOption("Choice", 3);
        picked.setStoryPoint(stubPoint);
        picked.setId(555L);
        when(optionRepo.findById(555L)).thenReturn(Optional.of(picked));

        when(selectionRepo.findByScenarioIdOrderByChapterNumberAsc(200L))
                .thenReturn(Collections.emptyList());

        String raw2 = "Next>O1>0>O2>1>O3>2";
        when(geminiService.getValidResponse(anyString(), any()))
                .thenReturn(raw2);

        // when
        ScenarioDto dto = service.continueScenario(200L, new ContinueScenarioRequest(555L));

        // then
        assertThat(dto.pointTotal()).isEqualTo(3);
        assertThat(dto.chapterCount()).isEqualTo(2);
        assertThat(dto.storyPoints()).hasSize(1);

        verify(selectionRepo).save(any(StoryPointSelection.class));
        verify(scenarioRepo, atLeastOnce()).save(scenario);
        verify(geminiService).getValidResponse(anyString(), any());
    }

    @Test
    void continueScenario_unknownScenario_throws() {
        when(scenarioRepo.findById(123L)).thenReturn(Optional.empty());
        assertThatThrownBy(() ->
                service.continueScenario(123L, new ContinueScenarioRequest(5L))
        ).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void continueScenario_unknownOption_throws() {
        Scenario s = new Scenario();
        s.setId(500L);
        s.setChapterCount(1);
        s.setCalamity(new Calamity());
        s.setHeroSelection(new HeroSelection());
        when(scenarioRepo.findById(500L)).thenReturn(Optional.of(s));
        when(optionRepo.findById(888L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.continueScenario(500L, new ContinueScenarioRequest(888L))
        ).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void continueScenario_pastChapter5_savesResultAndClosing() {
        // given a scenario at chapterCount = 5
        Scenario scenario = new Scenario();
        scenario.setId(300L);
        scenario.setChapterCount(5);
        scenario.setPointTotal(10);
        Calamity cal = new Calamity(); cal.setSeverity(Severity.LOW);
        scenario.setCalamity(cal);
        HeroSelection hs = new HeroSelection();
        hs.setHero1("A"); hs.setHero2("B"); hs.setHero3("C");
        scenario.setHeroSelection(hs);

        when(scenarioRepo.findById(300L)).thenReturn(Optional.of(scenario));

        StoryPoint stubPoint = new StoryPoint();
        stubPoint.setScenario(scenario);
        StoryPointOption picked = new StoryPointOption("X", 1);
        picked.setStoryPoint(stubPoint);
        picked.setId(999L);
        when(optionRepo.findById(999L)).thenReturn(Optional.of(picked));

        // override for final closing
        when(geminiService.generate(anyString())).thenReturn("🏁 Final Wrap");

        // when
        ScenarioDto dto = service.continueScenario(300L, new ContinueScenarioRequest(999L));

        // then
        assertThat(dto.chapterCount()).isEqualTo(6);
        assertThat(dto.closing()).isEqualTo("🏁 Final Wrap");
        verify(resultsService).addResult(eq(scenario), eq(true), eq(11));
    }

    @Test
    void getScenarioById_found() {
        Scenario scenario = new Scenario();
        scenario.setId(400L);
        Calamity cal = new Calamity(); cal.setId(7L); cal.setSeverity(Severity.HIGH);
        scenario.setCalamity(cal);
        HeroSelection hs = new HeroSelection();
        hs.setHero1("X"); hs.setHero2("Y"); hs.setHero3("Z");
        scenario.setHeroSelection(hs);

        when(scenarioRepo.findById(400L)).thenReturn(Optional.of(scenario));

        ScenarioDto dto = service.getScenarioById(400L);
        assertThat(dto.id()).isEqualTo(400L);
        assertThat(dto.heroes()).containsExactly("X","Y","Z");
    }

    @Test
    void getScenarioById_notFound_throws() {
        when(scenarioRepo.findById(123L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getScenarioById(123L))
                .isInstanceOf(ScenarioNotFoundException.class);
    }

    @Test
    void getScenarioInProgress_returnsDtos() {
        User user = new User(); user.setId(55L);
        when(userService.getCurrentUserBySession(httpRequest)).thenReturn(user);

        Scenario one = new Scenario();
        one.setId(1L);
        Calamity c1 = new Calamity(); c1.setId(10L); c1.setSeverity(Severity.LOW);
        one.setCalamity(c1);
        HeroSelection hs = new HeroSelection();
        hs.setHero1("H1"); hs.setHero2("H2"); hs.setHero3("H3");
        one.setHeroSelection(hs);

        when(scenarioRepo.findByUserIdAndCompleteFalse(55L))
                .thenReturn(List.of(one));

        List<ScenarioDto> dtos = service.getScenarioInProgress(httpRequest);
        assertThat(dtos).hasSize(1)
                .first().satisfies(d -> {
                    assertThat(d.id()).isEqualTo(1L);
                    assertThat(d.heroes()).containsExactly("H1","H2","H3");
                });
    }
}
