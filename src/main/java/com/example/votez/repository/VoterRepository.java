package com.example.votez.repository;

import com.example.votez.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {

    boolean existsByEmail(String email);
    Optional<Voter> findByEmail(String email);


}
