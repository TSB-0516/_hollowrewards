package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SummarizedBet {
    private User user;
    private Long wager;
    private Integer bets;
} 