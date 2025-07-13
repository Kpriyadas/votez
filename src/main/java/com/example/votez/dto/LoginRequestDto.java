package com.example.votez.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String secretCode;
}
