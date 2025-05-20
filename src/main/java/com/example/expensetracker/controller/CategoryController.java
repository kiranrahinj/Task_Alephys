package com.example.expensetracker.controller;

import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.TransactionType;
import com.example.expensetracker.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid Category category) {
        Category saved = categoryService.createCategory(category);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<Category>> getCategoriesByType(@PathVariable TransactionType type) {
        return ResponseEntity.ok(categoryService.getCategoriesByType(type));
    }
}