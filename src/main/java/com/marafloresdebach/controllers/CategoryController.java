package com.marafloresdebach.controllers;

import com.marafloresdebach.dto.*;
import com.marafloresdebach.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@Valid @RequestBody CategoryCreateDto dto) {
        CategoryResponseDto created = categoryService.create(dto);
        return ResponseEntity.created(URI.create("/api/categories/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable Long id,
                                                      @Valid @RequestBody CategoryUpdateDto dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> list(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            CategoryResponseDto dto = categoryService.getByName(name);
            return ResponseEntity.ok(dto == null ? List.of() : List.of(dto));
        }
        return ResponseEntity.ok(categoryService.getAll());
    }
}
