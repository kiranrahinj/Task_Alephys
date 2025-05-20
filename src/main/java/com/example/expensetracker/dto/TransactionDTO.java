package com.example.expensetracker.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    private String type;
    private BigDecimal amount;
    private Long categoryId;
    private String categoryName;
    private String description;
    private LocalDateTime date;
}