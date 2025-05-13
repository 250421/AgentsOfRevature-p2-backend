package com.anonymousibex.Agents.of.Revature.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anonymousibex.Agents.of.Revature.exception.CalamityNotFoundException;
import com.anonymousibex.Agents.of.Revature.exception.NoUserResultsFoundException;
import com.anonymousibex.Agents.of.Revature.model.Selections;
import com.anonymousibex.Agents.of.Revature.repository.CalamityRepository;
import com.anonymousibex.Agents.of.Revature.repository.SelectionsRepository;
import com.anonymousibex.Agents.of.Revature.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SelectionsService {
    private final SelectionsRepository selectionsRepository;
    private final UserRepository userRepository;
    private final CalamityRepository calamityRepository;

    public Selections addUserSelections (Selections selection){
        if(userRepository.findById(selection.getUser_id()).isPresent()){
            selection.setUser(userRepository.findById(selection.getUser_id()).get());
            if(calamityRepository.findById(selection.getCalamity_id()).isPresent()){
                selection.setCalamity(calamityRepository.findById(selection.getCalamity_id()).get());
                Selections savedSelection = selectionsRepository.save(selection);
                return savedSelection;
            }
            throw new CalamityNotFoundException("Calamity not found.");
        }
        throw new UsernameNotFoundException("User not found.");
    }

    public List<Selections> getUserSelections(Long id){
         Optional<List<Selections>> userSelections = selectionsRepository.findByUserId(id);
         if(userSelections.isPresent() && !userSelections.get().isEmpty()){
                    List<Selections> selections = userSelections.get();
                return selections;
         }
         throw new NoUserResultsFoundException("There are not selections for this user");
    }

    public Selections getCalamitySelections(Long id){
        Optional<Selections> Oselections = selectionsRepository.findByCalamityId(id);
        if(Oselections.isPresent() && Oselections.get() != null){
            Selections calamitySelections = Oselections.get();
            return calamitySelections;
        }
        throw new CalamityNotFoundException("Calamity not found.");
    }
}
