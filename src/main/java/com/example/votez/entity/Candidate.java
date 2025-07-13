package com.example.votez.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "party is required")
    private  String party;

    private int voteCount=0;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List <Vote> votes;

}
