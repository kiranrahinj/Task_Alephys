package com.example.expensetracker.service;

import com.example.expensetracker.dto.TransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class FileImportService {
    private static final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    public static List<TransactionDTO> importTransactions(String filePath) throws Exception {
        String content = Files.readString(Path.of(filePath));
        return Arrays.asList(objectMapper.readValue(content, TransactionDTO[].class));
    }
}