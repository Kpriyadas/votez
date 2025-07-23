package com.example.votez.controller;

import com.example.votez.entity.Voter;
import com.example.votez.service.VoterService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/voters")
@CrossOrigin
public class VoterController {

    private static final Logger logger = LoggerFactory.getLogger(VoterController.class);

    private VoterService voterService;

    @Autowired
    public void setVoterService(VoterService voterService) {
        this.voterService = voterService;
    }

    @PostMapping("/register")
    public ResponseEntity<Voter> registerVoter(@RequestBody @Valid Voter voter) {
        logger.info("Registering voter: {}", voter);
        Voter savedVoter = voterService.registerVoter(voter);
        return new ResponseEntity<>(savedVoter, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        return voterService.validateVoter(email)
                .map(voter -> ResponseEntity.ok().body("Login successful for " + voter.getName()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
    }


    @GetMapping
    public ResponseEntity<List<Voter>> getAllVoters() {
        logger.info("Fetching all voters");
        List<Voter> voters = voterService.getAllVoters();
        if (voters.isEmpty()) {
            logger.warn("No voters found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(voters, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voter> updateVoter(@PathVariable Long id, @RequestBody @Valid Voter voter) {
        logger.info("Updating voter with ID: {}", id);
        Voter existingVoter = voterService.getVoterById(id);
        if (existingVoter == null) {
            logger.warn("Voter  not found  with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Voter updatedVoter = voterService.UpdateVoter(id, voter);
        logger.info("Voter updated successfully: {}", updatedVoter);
        return new ResponseEntity<>(updatedVoter, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voter> getVoterById(@PathVariable Long id) {
        logger.info("Fetching voter by ID: {}", id);
        Voter voter = voterService.getVoterById(id);
        if (voter == null) {
            logger.warn("Voter  not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(voter, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoterById(@PathVariable Long id) {
        logger.info("Deleting voter with ID: {}", id);
        Voter voter = voterService.getVoterById(id);
        if (voter == null) {
            logger.warn("Voter not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        voterService.deleteVoter(id);
        logger.info("Voter deleted with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 is more appropriate
    }
}
