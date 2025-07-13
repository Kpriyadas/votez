
package com.example.votez.service;

import com.example.votez.entity.Candidate;
import com.example.votez.entity.ElectionResult;
import com.example.votez.exception.ResourceNotFoundException;
import com.example.votez.repository.CandidateRepository;
import com.example.votez.repository.ElectionRepository;
import com.example.votez.repository.VoteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ElectionResultService {

    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;
    private final VoteRepository voteRepository;

    public ElectionResultService(CandidateRepository candidateRepository, VoteRepository voteRepository, ElectionRepository electionRepository) {
        this.candidateRepository = candidateRepository;
        this.voteRepository = voteRepository;
        this.electionRepository = electionRepository;
    }

    public ElectionResult declareElectionResult(String electionName) {
        // Check if result already exists
        Optional<ElectionResult> existingResult = electionRepository.findByElectionName(electionName);
        if (existingResult.isPresent()) {
            return existingResult.get();
        }
        List<Candidate> allCandidates = candidateRepository.findAllByOrderByVoteCountDesc();

        // Check if any vote has been cast
        if (voteRepository.count() == 0) {
            throw new IllegalStateException("Cannot declare the result as no vote has been cast.");
        }

        // Get candidates sorted by votes


        if (allCandidates.isEmpty()) {
            throw new ResourceNotFoundException("No candidates available.");
        }

        allCandidates.forEach(c ->
                System.out.println("Candidate ID: " + c.getId() + ", Name: " + c.getName() + ", Votes: " + c.getVoteCount()));

        // Winner = first in sorted list
        Candidate winner = allCandidates.get(0);


        // Total votes
        int totalVotes = (int) voteRepository.count();

        // Create result
        ElectionResult electionResult = new ElectionResult();
        electionResult.setElectionName(electionName);
        electionResult.setWinner(winner);
        electionResult.setTotalVotes(totalVotes);

        return electionRepository.save(electionResult);
    }

    public List<ElectionResult> getAllResults() {

        return electionRepository.findAll();
    }
}

