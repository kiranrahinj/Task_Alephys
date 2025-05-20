package com.example.expensetracker.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class MonthlyReport {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    
    public BigDecimal getNetAmount() {
        return totalIncome.subtract(totalExpense);
    }
}