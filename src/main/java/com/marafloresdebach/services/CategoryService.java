package com.marafloresdebach.services;

import com.marafloresdebach.dto.*;
import java.util.List;

public interface CategoryService {
    CategoryResponseDto create(CategoryCreateDto dto);
    CategoryResponseDto update(Long id, CategoryUpdateDto dto);
    void delete(Long id);

    CategoryResponseDto getById(Long id);
    List<CategoryResponseDto> getAll();

    CategoryResponseDto getByName(String name); // null si no existe
}
