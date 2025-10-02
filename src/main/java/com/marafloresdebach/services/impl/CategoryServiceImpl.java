package com.marafloresdebach.services.impl;

import com.marafloresdebach.dto.*;
import com.marafloresdebach.entities.Category;
import com.marafloresdebach.mappers.CategoryMapper;
import com.marafloresdebach.repositories.CategoryRepository;
import com.marafloresdebach.repositories.ProductRepository;
import com.marafloresdebach.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public CategoryResponseDto create(CategoryCreateDto dto) {
        String name = dto.getName().trim();
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new RuntimeException("Category name already exists");
        }
        Category saved = categoryRepository.save(CategoryMapper.toEntity(dto));
        return CategoryMapper.toResponse(saved);
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryUpdateDto dto) {
        Category db = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));

        String name = dto.getName().trim();
        if (!db.getName().equalsIgnoreCase(name)
                && categoryRepository.existsByNameIgnoreCase(name)) {
            throw new RuntimeException("Category name already exists");
        }

        CategoryMapper.merge(db, dto);
        return CategoryMapper.toResponse(categoryRepository.save(db));
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found: " + id);
        }
        if (productRepository.existsByCategoryId(id)) {
            throw new RuntimeException("Category has products and cannot be deleted");
        }
        categoryRepository.deleteById(id);
    }

    @Override @Transactional(readOnly = true)
    public CategoryResponseDto getById(Long id) {
        return CategoryMapper.toResponse(
                categoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Category not found: " + id))
        );
    }

    @Override @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findAll()
                .stream().map(CategoryMapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public CategoryResponseDto getByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .map(CategoryMapper::toResponse)
                .orElse(null);
    }
}
