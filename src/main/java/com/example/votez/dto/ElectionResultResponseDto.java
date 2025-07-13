package com.example.votez.dto;

import lombok.Data;

@Data
public class ElectionResultResponseDto {
    private String electionName;
    private int totalVotes;
    private Long winnerId;
    private int winnerVotes;
}
