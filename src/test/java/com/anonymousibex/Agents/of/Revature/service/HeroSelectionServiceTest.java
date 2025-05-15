package com.anonymousibex.Agents.of.Revature.service;

import com.anonymousibex.Agents.of.Revature.exception.*;
import com.anonymousibex.Agents.of.Revature.model.Calamity;
import com.anonymousibex.Agents.of.Revature.model.HeroSelection;
import com.anonymousibex.Agents.of.Revature.model.User;
import com.anonymousibex.Agents.of.Revature.repository.CalamityRepository;
import com.anonymousibex.Agents.of.Revature.repository.HeroSelectionRepository;
import com.anonymousibex.Agents.of.Revature.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HeroSelectionServiceTest {

    @Mock
    private HeroSelectionRepository selectionsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CalamityRepository calamityRepository;

    @InjectMocks
    private HeroSelectionService heroSelectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddHeroSelections_Success() {
        HeroSelection selection = new HeroSelection();
        User user = new User();
        user.setId(1L);
        Calamity calamity = new Calamity();
        calamity.setId(1L);
        selection.setUser(user);
        selection.setCalamity(calamity);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(calamityRepository.findById(1L)).thenReturn(Optional.of(calamity));
        when(selectionsRepository.save(selection)).thenReturn(selection);

        HeroSelection result = heroSelectionService.addHeroSelections(selection);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(1L);
        verify(calamityRepository, times(1)).findById(1L);
        verify(selectionsRepository, times(1)).save(selection);
    }

    @Test
    void testAddHeroSelections_UserNotFound() {
        HeroSelection selection = new HeroSelection();
        User user = new User();
        user.setId(1L);
        selection.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> heroSelectionService.addHeroSelections(selection));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testAddHeroSelections_CalamityNotFound() {
        HeroSelection selection = new HeroSelection();
        User user = new User();
        user.setId(1L);
        Calamity calamity = new Calamity();
        calamity.setId(1L);
        selection.setUser(user);
        selection.setCalamity(calamity);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(calamityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CalamityNotFoundException.class, () -> heroSelectionService.addHeroSelections(selection));
        verify(userRepository, times(1)).findById(1L);
        verify(calamityRepository, times(1)).findById(1L);
    }

    @Test
    void testGetHeroSelectionsByUser_Success() {
        HeroSelection selection1 = new HeroSelection();
        HeroSelection selection2 = new HeroSelection();
        when(selectionsRepository.findByUserId(1L)).thenReturn(Optional.of(Arrays.asList(selection1, selection2)));

        List<HeroSelection> results = heroSelectionService.getHeroSelectionsByUser(1L);

        assertNotNull(results);
        assertEquals(2, results.size());
        verify(selectionsRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetHeroSelectionsByUser_NoSelections() {
        when(selectionsRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(NoUserResultsFoundException.class, () -> heroSelectionService.getHeroSelectionsByUser(1L));
        verify(selectionsRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetCalamitySelections_Success() {
        HeroSelection selection = new HeroSelection();
        when(selectionsRepository.findByCalamityId(1L)).thenReturn(Optional.of(selection));

        HeroSelection result = heroSelectionService.getCalamitySelections(1L);

        assertNotNull(result);
        verify(selectionsRepository, times(1)).findByCalamityId(1L);
    }

    @Test
    void testGetCalamitySelections_NotFound() {
        when(selectionsRepository.findByCalamityId(1L)).thenReturn(Optional.empty());

        assertThrows(CalamityNotFoundException.class, () -> heroSelectionService.getCalamitySelections(1L));
        verify(selectionsRepository, times(1)).findByCalamityId(1L);
    }
}
