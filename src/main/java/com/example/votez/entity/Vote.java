package com.example.votez.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.web.JsonPath;

@Entity
@Data

public class Vote {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "voter_id", unique = true, nullable = false)
    @JsonIgnore
    private Voter voter;

    @ManyToOne
    @JoinColumn(name = "candidate")
    @JsonIgnore
    private Candidate candidate;

    @JsonGetter("voterId")
    public Long getVoterId() {
        return voter != null ? voter.getId() : null;
    }

    @JsonGetter("candidateId")
    public Long getCandidateId() {
        return candidate != null ? candidate.getId() : null;
    }

}