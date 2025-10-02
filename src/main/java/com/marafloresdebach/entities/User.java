package com.marafloresdebach.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

@Entity
@Table(name = "\"user\"") // escapado porque es palabra reservada
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

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
