package com.example.votez.service;

import com.example.votez.entity.Candidate;
import com.example.votez.entity.Vote;
import com.example.votez.entity.Voter;
import com.example.votez.exception.DuplicateResourceException;
import com.example.votez.exception.ResourceNotFoundException;
import com.example.votez.repository.CandidateRepository;
import com.example.votez.repository.VoterRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoterService {

    private static final Logger logger = LoggerFactory.getLogger(VoterService.class);

    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;

    @Autowired
    public VoterService(VoterRepository voterRepository, CandidateRepository candidateRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
    }

    public Voter registerVoter(Voter voter) {
        logger.info("Attempting to register voter with email: {}", voter.getEmail());

        if (voterRepository.existsByEmail(voter.getEmail())) {
            logger.warn("Duplicate voter registration attempt for email: {}", voter.getEmail());
            throw new DuplicateResourceException("Voter with email " + voter.getEmail() + " already exists");
        }
        if (voter.getHasVoted() == null) {
            voter.setHasVoted(false);
            logger.info("Setting hasVoted to false as it was null");
        }


        Voter saved = voterRepository.save(voter);
        logger.info("Voter registered successfully with ID: {}", saved.getId());
        return saved;
    }

    public List<Voter> getAllVoters() {
        logger.info("Fetching all voters");
        return voterRepository.findAll();
    }

    public Voter getVoterById(long id) {
        logger.info("Fetching voter by ID: {}", id);
        return voterRepository.findById(id).orElseThrow(() -> {
            logger.error("Voter not found with ID: {}", id);
            return new ResourceNotFoundException("Voter with id " + id + " not  found");
        });
    }

    public Voter UpdateVoter(Long id, Voter updateVoter) {
        logger.info("Updating voter with ID: {}", id);
        Voter voter = voterRepository.findById(id).orElseThrow(() -> {
            logger.error("Voter not found for update, ID: {}", id);
            return new ResourceNotFoundException("Voter with id " + id + " not found");
        });

        voter.setName(updateVoter.getName());
        voter.setEmail(updateVoter.getEmail());

        Voter updated = voterRepository.save(voter);
        logger.info("Voter updated successfully, ID: {}", updated.getId());
        return updated;
    }

    @Transactional
    public void deleteVoter(long id) {
        logger.info("Deleting voter with ID: {}", id);
        Voter voter = voterRepository.findById(id).orElseThrow(() -> {
            logger.error("Voter not found for deletion, ID: {}", id);
            return new ResourceNotFoundException("Cannot delete: Voter with id " + id + " not found");
        });

        Vote vote = voter.getVote();
        if (vote != null) {
            Candidate candidate = vote.getCandidate();
            candidate.setVoteCount(candidate.getVoteCount() - 1);
            candidateRepository.save(candidate);
            logger.info("Decremented vote count for candidate ID: {}", candidate.getId());
        }

        voterRepository.deleteById(id);
        logger.info("Voter deleted successfully, ID: {}", id);
    }
    public Optional<Voter> validateVoter(String email) {
        logger.info("Validating voter with email: {}", email);
        return voterRepository.findByEmail(email);
    }


}
