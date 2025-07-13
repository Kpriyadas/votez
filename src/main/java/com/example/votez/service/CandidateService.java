package com.example.votez.service;

import com.example.votez.entity.Candidate;
import com.example.votez.entity.Vote;
import com.example.votez.exception.ResourceNotFoundException;
import com.example.votez.repository.CandidateRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;


import java.util.List;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {

        this.candidateRepository = candidateRepository;
    }

    public Candidate addCandidate(Candidate candidate) {

        return candidateRepository.save(candidate);
        }

        public List<Candidate> getAllCandidates() {

            return  candidateRepository.findAll();
    }

    public Candidate getCandidateById(Long id) {
       Candidate candidate = candidateRepository.findById(id).orElse(null);
       if (candidate == null) {
           throw new ResourceNotFoundException("Candidate not found");
        }
       return candidate;
    }

    public Candidate updateCandidate(long id,Candidate updatedcandidate) {
        Candidate candidate1 = getCandidateById(id);
        if (updatedcandidate.getName() != null) {
           candidate1.setName(updatedcandidate.getName());
        }
        if(updatedcandidate.getParty() != null) {
            candidate1.setParty(updatedcandidate.getParty());
        }
        return candidateRepository.save(candidate1);
    }

    public void deleteCandidate(long id) {
        Candidate candidate = getCandidateById(id);
        List<Vote> votes = candidate.getVotes();
        for (Vote v : votes) {
            v.setCandidate(null);
        }
        candidate.getVotes().clear();
        candidateRepository.delete(candidate);
    }
}
