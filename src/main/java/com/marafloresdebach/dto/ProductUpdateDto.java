package com.marafloresdebach.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductUpdateDto {
    @NotBlank
    @Size(max = 128)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer stock;

    @NotNull
    private Long categoryId;
}
