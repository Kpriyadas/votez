package com.example.votez.controller;

import com.example.votez.dto.VoteResponseDto;
import com.example.votez.entity.Vote;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.votez.service.VoteService;
import com.example.votez.dto.VoteRequestDto;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin
public class VotingController {


    private VoteService  voteService;

    @Autowired
    public VotingController (VoteService  voteService){
        this.voteService = voteService;
    }

    @PostMapping("/cast")
    public ResponseEntity<VoteResponseDto> castVote (@RequestBody @Valid VoteRequestDto voteRequestDto){
        Vote vote=voteService.castVote(voteRequestDto.getVoterId(),voteRequestDto.getCandidateId());
        VoteResponseDto voteResponseDto=new VoteResponseDto("Vote successfully casted", true, voteRequestDto.getVoterId(),voteRequestDto.getCandidateId());
        return  new ResponseEntity<>(voteResponseDto,HttpStatus.CREATED);
    }

    @GetMapping("/allvotes")
    public ResponseEntity<List<Vote>> getAllVotes() {
        List<Vote> voteList = voteService.getVotes();
        return new ResponseEntity<>(voteList, HttpStatus.OK);
    }

}


