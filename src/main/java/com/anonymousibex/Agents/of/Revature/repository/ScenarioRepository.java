package com.anonymousibex.Agents.of.Revature.repository;

import com.anonymousibex.Agents.of.Revature.model.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    List<Scenario> findByUserIdAndCompleteFalse(Long userId);
}
