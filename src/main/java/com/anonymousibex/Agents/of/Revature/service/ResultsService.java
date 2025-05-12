package com.anonymousibex.Agents.of.Revature.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anonymousibex.Agents.of.Revature.exception.CalamityNotFoundException;
import com.anonymousibex.Agents.of.Revature.exception.NoUserResultsFoundException;
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
        if(results.isPresent()){
            List<Results> userResults = results.get();
            return userResults;
        }
        throw new NoUserResultsFoundException("There are not results for this user");
    }

    public Results AddResult(Results result){
        if(calamityRepository.findById(result.getCalamity_id()).isPresent()){
            result.setCalamity(calamityRepository.findById(result.getCalamity_id()).get());
        }
        else{
            throw new UsernameNotFoundException("User not found.");
        }

        if(userRepository.findById(result.getUser_id()).isPresent()){
            result.setUser(userRepository.findById(result.getUser_id()).get());
        }
        else{
             throw new CalamityNotFoundException("Calamity not found.");
        }
        
        resultsRepository.save(result);
        return result;
    }

}
