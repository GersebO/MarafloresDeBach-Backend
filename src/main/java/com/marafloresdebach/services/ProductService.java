package com.marafloresdebach.services;

import com.marafloresdebach.dto.*;

import java.util.List;

public interface ProductService {
    ProductResponseDto create(ProductCreateDto dto);
    ProductResponseDto update(Long id, ProductUpdateDto dto);
    void delete(Long id);

    ProductResponseDto getById(Long id);
    List<ProductResponseDto> getAll();

    List<ProductResponseDto> searchByName(String q);
    List<ProductResponseDto> findByCategory(Long categoryId);
    
}
