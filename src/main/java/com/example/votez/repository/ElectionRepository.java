package com.example.votez.repository;

import com.example.votez.entity.ElectionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElectionRepository  extends JpaRepository<ElectionResult,Long> {

    Optional<ElectionResult> findByElectionName(String electionName);
}
