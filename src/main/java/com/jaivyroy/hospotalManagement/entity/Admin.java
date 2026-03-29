package com.jaivyroy.hospotalManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admin")  // Optional: You can specify table name here
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNull(message = "Username must be required")
    @Size(min = 2, max = 60)
    private String username;

    @NotNull
    @Email(message = "Must Enter a valid email")
    private String email;

    @NotNull
    @Size(min = 2, max = 50) // Example size constraints for role
    private String role;  // Corrected typo from "rool" to "role"

    @NotNull
    private Long departmentId; // Changed to Long or reference to Department entity

    @CreationTimestamp
    private LocalDateTime createdAt;  // Changed to LocalDateTime

    private LocalDateTime lastLogin;  // Changed to LocalDateTime

    private boolean isActive;

}