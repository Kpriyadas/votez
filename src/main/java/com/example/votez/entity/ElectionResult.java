package com.example.votez.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class ElectionResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="electionName is required")
    private String electionName;

    private int totalVotes;


    @OneToOne
    @JoinColumn(name="winner_db",referencedColumnName = "id")
    private Candidate winner;

}
