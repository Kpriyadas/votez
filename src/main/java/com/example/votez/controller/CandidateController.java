package com.example.votez.controller;

import com.example.votez.entity.Candidate;
import com.example.votez.repository.CandidateRepository;
import com.example.votez.service.CandidateService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@CrossOrigin
public class CandidateController {

    private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);

    private final CandidateRepository candidateRepository;
    private final CandidateService candidateService;

    public CandidateController(CandidateRepository candidateRepository, CandidateService candidateService) {
        this.candidateRepository = candidateRepository;
        this.candidateService = candidateService;
    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody @Valid Candidate candidate) {
        logger.info("Creating new candidate: {}", candidate);
        Candidate savedCandidate = candidateService.addCandidate(candidate);
        logger.info("Candidate created with ID: {}", savedCandidate.getId());
        return new ResponseEntity<>(savedCandidate, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        logger.info("Fetching all candidates");
        List<Candidate> candidates = this.candidateService.getAllCandidates();
        logger.info("Returning {} candidates", candidates.size());
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        logger.info("Fetching candidate with ID: {}", id);
        Candidate candidate = this.candidateService.getCandidateById(id);
        logger.info("Found candidate: {}", candidate);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @RequestBody @Valid Candidate updatedCandidate) {
        logger.info("Updating candidate with ID: {}", id);
        Candidate updated = candidateService.updateCandidate(id, updatedCandidate);
        logger.info("Candidate updated successfully: {}", updated);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Candidate> deleteCandidateById(@PathVariable Long id) {
        logger.info("Deleting candidate with ID: {}", id);
        candidateService.deleteCandidate(id);
        logger.info("Candidate deleted successfully with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
