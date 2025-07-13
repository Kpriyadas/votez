package com.example.votez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponseDto {
    private String message;
    private boolean success;
    private Long voterId;
    private Long CandidateId;

}
