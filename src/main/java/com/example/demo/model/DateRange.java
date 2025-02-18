package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DateRange {
    private LocalDateTime from;
    private LocalDateTime to;
} 