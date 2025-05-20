package com.example.expensetracker.service;

import com.example.expensetracker.Exception.CustomException.CategoryNotFoundException;
import com.example.expensetracker.dto.MonthlyReport;
import com.example.expensetracker.dto.TransactionDTO;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.model.TransactionType;
import com.example.expensetracker.repository.CategoryRepository;
import com.example.expensetracker.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new CategoryNotFoundException("Category is not found.. Please check category"));

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.valueOf(dto.getType()));
        transaction.setAmount(dto.getAmount());
        transaction.setCategory(category);
        transaction.setDescription(dto.getDescription());
        transaction.setDate(dto.getDate() != null ? dto.getDate() : LocalDateTime.now());

        Transaction saved = transactionRepository.save(transaction);
        return convertToDTO(saved);
    }

    public List<TransactionDTO> getMonthlyTransactions(int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.with(TemporalAdjusters.lastDayOfMonth());

        return transactionRepository.findByDateBetweenOrderByDateDesc(start, end)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public MonthlyReport getMonthlyReport(int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.with(TemporalAdjusters.lastDayOfMonth());

        BigDecimal totalIncome = transactionRepository.sumByTypeAndDateBetween(
            TransactionType.INCOME, start, end);
        BigDecimal totalExpense = transactionRepository.sumByTypeAndDateBetween(
            TransactionType.EXPENSE, start, end);

        return MonthlyReport.builder()
            .totalIncome(totalIncome != null ? totalIncome : BigDecimal.ZERO)
            .totalExpense(totalExpense != null ? totalExpense : BigDecimal.ZERO)
            .build();
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setType(transaction.getType().name());
        dto.setAmount(transaction.getAmount());
        dto.setCategoryId(transaction.getCategory().getId());
        dto.setCategoryName(transaction.getCategory().getName());
        dto.setDescription(transaction.getDescription());
        dto.setDate(transaction.getDate());
        return dto;
    }
}