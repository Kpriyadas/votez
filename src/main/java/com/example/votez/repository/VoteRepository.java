package com.example.votez.repository;

import com.example.votez.entity.Candidate;
import com.example.votez.entity.ElectionResult;
import com.example.votez.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    Map<Long, Candidate> candidateMap = new HashMap<>();
}
