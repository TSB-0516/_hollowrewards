package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AffiliateInfo {
    private Long id;
    private String code;
    private LocalDateTime createdAt;
    private Long claimableBalance;
    private Long totalEarned;
    private Long totalWagered;
} 