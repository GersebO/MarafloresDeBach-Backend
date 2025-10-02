package com.marafloresdebach.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer") 
public class Customer {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 12)
    private String run;

    private String name;

    private String lastName;

    private LocalDate birthDate;

    @Column(unique = true, nullable = false)
    private String email;

    private String address;

    private String region;

    private String comuna;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // ✅ entra por JSON, no sale en la respuesta
    @Column(nullable = false)
    private String password;
}
