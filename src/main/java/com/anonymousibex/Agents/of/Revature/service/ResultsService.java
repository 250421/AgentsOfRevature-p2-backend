package com.anonymousibex.Agents.of.Revature.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anonymousibex.Agents.of.Revature.exception.*;
import com.anonymousibex.Agents.of.Revature.model.Results;

import com.anonymousibex.Agents.of.Revature.repository.CalamityRepository;
import com.anonymousibex.Agents.of.Revature.repository.ResultsRepository;
import com.anonymousibex.Agents.of.Revature.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultsService {
    private final ResultsRepository resultsRepository;
    private final CalamityRepository calamityRepository;
    private final UserRepository userRepository;

    public List<Results> getAllResults(){
        List<Results> results = resultsRepository.findAll();
        return results;
    }

     public List<Results> getAllUserResults(Long userId){
        Optional<List<Results>> results = resultsRepository.findByUserId(userId);
        if(results.isPresent() && !results.get().isEmpty()){
            List<Results> userResults = results.get();
            return userResults;
        }
        throw new NoUserResultsFoundException("There are not results for this user");
    }

    public Results AddResult(Results result){
        if(userRepository.findById(result.getUser_id()).isPresent()){
            result.setUser(userRepository.findById(result.getUser_id()).get());
            if(calamityRepository.findById(result.getCalamity_id()).isPresent()){
                result.setCalamity(calamityRepository.findById(result.getCalamity_id()).get());
                resultsRepository.save(result);
                return result;
            }
            throw new CalamityNotFoundException("Calamity not found.");
        }
        throw new UsernameNotFoundException("User not found.");
    }

    public Results UpdateResult(Results result){
        if(resultsRepository.findById(result.getId()).isPresent()){
            Results resultToUpdate = resultsRepository.findById(result.getId()).get();
            resultToUpdate.setRepGained(result.getRepGained());
            resultToUpdate.setDidWin(result.isDidWin());
            resultsRepository.save(resultToUpdate);
            return resultToUpdate;
        }
         throw new NoUserResultsFoundException("No result found.");
       
    }

}
