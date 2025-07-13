package com.example.votez.service;

import com.example.votez.entity.Candidate;
import com.example.votez.entity.Vote;
import com.example.votez.entity.Voter;
import com.example.votez.exception.ResourceNotFoundException;
import com.example.votez.repository.CandidateRepository;
import com.example.votez.repository.VoteRepository;
import com.example.votez.repository.VoterRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);

    private final VoteRepository voteRepository;
    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;

    public VoteService(VoteRepository voteRepository, CandidateRepository candidateRepository, VoterRepository voterRepository) {
        this.voteRepository = voteRepository;
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
    }

    @Transactional
    public Vote castVote(Long voterId, Long candidateId) {
        logger.info("Casting vote for voterId: {} and candidateId: {}", voterId, candidateId);

        if (!voterRepository.existsById(voterId)) {
            logger.error("Voter with ID {} not found", voterId);
            throw new ResourceNotFoundException("Voter not found");
        }

        if (!candidateRepository.existsById(candidateId)) {
            logger.error("Candidate with ID {} not found", candidateId);
            throw new ResourceNotFoundException("Candidate not found");
        }

        Voter voter = voterRepository.findById(voterId).get();

        if (voter.getHasVoted()) {
            logger.warn("Voter with ID {} has already voted", voterId);
            throw new ResourceNotFoundException("Voter is already voted");
        }

        Candidate candidate = candidateRepository.findById(candidateId).get();

        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setCandidate(candidate);

        logger.info("Incrementing vote count for candidate ID {}", candidateId);
        candidate.setVoteCount(candidate.getVoteCount() + 1);
        candidateRepository.save(candidate);

        voter.setVote(vote);
        voter.setHasVoted(true);
        voterRepository.save(voter);

        logger.info("Vote cast successfully for voter ID {}", voterId);
        return vote;
    }

    public List<Vote> getVotes() {
        logger.info("Fetching all votes");
        return voteRepository.findAll();
    }
}
