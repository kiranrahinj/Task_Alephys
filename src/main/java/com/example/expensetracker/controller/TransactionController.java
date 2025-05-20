package com.example.expensetracker.controller;

import com.example.expensetracker.dto.MonthlyReport;
import com.example.expensetracker.dto.TransactionDTO;
import com.example.expensetracker.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transaction) {
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<List<TransactionDTO>> getMonthlyTransactions(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(transactionService.getMonthlyTransactions(year, month));
    }

    @GetMapping("/report/{year}/{month}")
    public ResponseEntity<MonthlyReport> getMonthlyReport(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(transactionService.getMonthlyReport(year, month));
    }
}