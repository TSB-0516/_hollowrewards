package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Summary {
    private Integer totalUsers;
    private Integer activeUsers;
    private Integer totalBets;
    private Long totalWager;
} 