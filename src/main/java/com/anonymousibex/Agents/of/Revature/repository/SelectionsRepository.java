package com.anonymousibex.Agents.of.Revature.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anonymousibex.Agents.of.Revature.model.Selections;


public interface SelectionsRepository extends JpaRepository<Selections, Long>{
    Optional<Selections> findByCalamityId(long id);
}
