package com.example.votez.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Data
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message="name is required")
    private String name;

    @NotNull(message = "email is required")
    @Email(message = "invalid email")
    private  String email;


    @NotNull
    private Boolean hasVoted = false;


    @OneToOne(mappedBy ="voter", cascade = CascadeType.ALL)
    @JsonIgnore
    private Vote vote;

    @Column(nullable = false)
    private String role ;  // or "admin"





}
