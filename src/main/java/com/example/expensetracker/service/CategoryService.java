package com.example.expensetracker.service;

import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.TransactionType;
import com.example.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
    public List<Category> getCategoriesByType(TransactionType type) {
        return categoryRepository.findByType(type);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}