package com.marafloresdebach.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 64)
  private String name;

  @Column(length = 255)
  private String description;
}
