package com.kiingdom.streamingsite.service.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kiingdom.streamingsite.model.content.Category;
import com.kiingdom.streamingsite.repository.CategoryRepository;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
