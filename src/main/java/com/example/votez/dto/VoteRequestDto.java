package com.example.votez.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteRequestDto {

    @NotNull(message="Candidate ID is required")
    private Long candidateId;

    @NotNull(message="Voter ID is required")
    private Long voterId;
}
