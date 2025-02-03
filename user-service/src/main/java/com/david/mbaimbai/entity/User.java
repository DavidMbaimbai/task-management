package com.david.mbaimbai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "full_name", unique = true, nullable = false)
    private String fullName;
    @Email
    @Column(name = "email", unique = true, nullable = false)
    @NotBlank()
    private String email;
    @Column(name = "password", unique = true, nullable = false)
    private String password;
    private String role;
}
