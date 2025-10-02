package com.marafloresdebach.mappers;

import com.marafloresdebach.dto.*;
import com.marafloresdebach.entities.Category;

public final class CategoryMapper {
    private CategoryMapper() {}

    public static Category toEntity(CategoryCreateDto dto) {
        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public static void merge(Category entity, CategoryUpdateDto dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }

    public static CategoryResponseDto toResponse(Category entity) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
