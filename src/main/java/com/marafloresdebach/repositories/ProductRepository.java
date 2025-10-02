package com.marafloresdebach.repositories;

import com.marafloresdebach.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByNameContainingIgnoreCase(String q);
  List<Product> findByCategoryId(Long categoryId);
  boolean existsByCategoryId(Long categoryId); // usado para evitar borrar categories con productos
}
