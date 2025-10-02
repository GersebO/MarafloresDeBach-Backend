package com.marafloresdebach.mappers;

import com.marafloresdebach.dto.ProductCreateDto;
import com.marafloresdebach.dto.ProductResponseDto;
import com.marafloresdebach.dto.ProductUpdateDto;
import com.marafloresdebach.entities.Category;
import com.marafloresdebach.entities.Product;

public final class ProductMapper {
    private ProductMapper() { }

    public static Product toEntity(ProductCreateDto dto, Category category) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .category(category)
                .build();
    }

    public static void merge(Product target, ProductUpdateDto dto, Category category) {
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setPrice(dto.getPrice());
        target.setStock(dto.getStock());
        target.setCategory(category);
    }

    public static ProductResponseDto toResponse(Product p) {
        ProductResponseDto r = new ProductResponseDto();
        r.setId(p.getId());
        r.setName(p.getName());
        r.setDescription(p.getDescription());
        r.setPrice(p.getPrice());
        r.setStock(p.getStock());
        if (p.getCategory() != null) {
            r.setCategoryId(p.getCategory().getId());
            r.setCategoryName(p.getCategory().getName());
        }
        return r;
    }
}
