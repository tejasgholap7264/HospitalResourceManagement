package com.jaivyroy.hospotalManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = false, length = 50)
    private String policyNumber;

    @Column(nullable = false, length = 100)
    private String provider;

    // Changed validUnit to LocalDateTime
    @Column(nullable = false)
    private LocalDateTime validUnit; // changed from String to LocalDateTime

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // Changed to 'createdAt' for clarity

    @OneToOne(mappedBy = "insurance") // inverse side
    private Patient patient;
}
