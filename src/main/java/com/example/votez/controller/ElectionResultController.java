package com.example.votez.controller;

import com.example.votez.dto.ElectionResultResponseDto;
import com.example.votez.entity.ElectionResult;
import com.example.votez.service.ElectionResultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/election/result")
@CrossOrigin
public class ElectionResultController {

    private final ElectionResultService electionResultService;


    @Autowired
    public ElectionResultController(ElectionResultService electionResultService) {
        this.electionResultService = electionResultService;

    }

    @PostMapping("/declare")
    public ResponseEntity<ElectionResultResponseDto> declareElectionResult(@RequestBody @Valid ElectionResultResponseDto electionResultdto) {
        ElectionResult result = electionResultService.declareElectionResult(electionResultdto.getElectionName());

        ElectionResultResponseDto responseDto = new ElectionResultResponseDto();
        responseDto.setElectionName(result.getElectionName());
        responseDto.setTotalVotes(result.getTotalVotes());
        responseDto.setWinnerId(result.getWinner().getId());
        responseDto.setWinnerVotes(result.getWinner().getVoteCount());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 🔐 Admin-protected endpoint
    @GetMapping("/results")
    public ResponseEntity<List<ElectionResult>> getResults(@RequestHeader("Authorization") String token) throws AccessDeniedException {
        String jwt = token.replace("Bearer ", "");



        List<ElectionResult> resultList = electionResultService.getAllResults();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @GetMapping("/allresult")
   public ResponseEntity<List<ElectionResult>> getAllElectionResult(){
       List<ElectionResult> resultlist = electionResultService.getAllResults();
       return new ResponseEntity<>(resultlist, HttpStatus.OK);
    }
}
