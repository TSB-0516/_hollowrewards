package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class AffiliateData {
    private AffiliateInfo affiliate;
    private List<SummarizedBet> summarizedBets;
    private Summary summary;
    private DateRange dateRange;
    private long processingTime;
} 