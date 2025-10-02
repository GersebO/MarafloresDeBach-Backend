package com.marafloresdebach.controllers;

import com.marafloresdebach.dto.*;
import com.marafloresdebach.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody ProductCreateDto dto) {
    ProductResponseDto created = productService.create(dto);
    return ResponseEntity.created(URI.create("/api/products/" + created.getId())).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDto> update(@PathVariable Long id,
                                                   @Valid @RequestBody ProductUpdateDto dto) {
    return ResponseEntity.ok(productService.update(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    productService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id) {
    return ResponseEntity.ok(productService.getById(id));
  }

  @GetMapping
  public ResponseEntity<List<ProductResponseDto>> list(
      @RequestParam(required = false) String q,
      @RequestParam(required = false) Long categoryId) {

    if (q != null && !q.isBlank()) return ResponseEntity.ok(productService.searchByName(q));
    if (categoryId != null) return ResponseEntity.ok(productService.findByCategory(categoryId));
    return ResponseEntity.ok(productService.getAll());
  }
  
}
