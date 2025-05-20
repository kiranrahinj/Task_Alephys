package com.example.expensetracker.repository;

import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByDateBetweenOrderByDateDesc(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = ?1 AND t.date BETWEEN ?2 AND ?3")
    BigDecimal sumByTypeAndDateBetween(TransactionType type, LocalDateTime start, LocalDateTime end);
}