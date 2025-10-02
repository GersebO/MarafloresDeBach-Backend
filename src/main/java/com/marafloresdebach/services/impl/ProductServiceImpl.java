package com.marafloresdebach.services.impl;

import com.marafloresdebach.dto.*;
import com.marafloresdebach.entities.Category;
import com.marafloresdebach.entities.Product;
import com.marafloresdebach.mappers.ProductMapper;
import com.marafloresdebach.repositories.CategoryRepository;
import com.marafloresdebach.repositories.ProductRepository;
import com.marafloresdebach.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDto create(ProductCreateDto dto) {
        Category cat = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId()));

        Product entity = ProductMapper.toEntity(dto, cat);
        Product saved = productRepository.save(entity);
        return ProductMapper.toResponse(saved);
    }

    @Override
    public ProductResponseDto update(Long id, ProductUpdateDto dto) {
        Product db = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        Category cat = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId()));

        ProductMapper.merge(db, dto, cat);
        Product saved = productRepository.save(db);
        return ProductMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        return ProductMapper.toResponse(p);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> searchByName(String q) {
        return productRepository.findByNameContainingIgnoreCase(q).stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
}
