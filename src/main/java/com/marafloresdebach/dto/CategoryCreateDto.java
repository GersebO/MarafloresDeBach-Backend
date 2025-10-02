package com.marafloresdebach.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryCreateDto {
    @NotBlank
    @Size(max = 64)
    private String name;

    @Size(max = 255)
    private String description;
}
