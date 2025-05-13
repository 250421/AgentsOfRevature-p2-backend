package com.anonymousibex.Agents.of.Revature.repository;

import com.anonymousibex.Agents.of.Revature.model.UserSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserSelectionRepository extends JpaRepository<UserSelection, Long> {
    List<UserSelection> findByScenarioIdOrderByChapterNumberAsc(Long scenarioId);
}
