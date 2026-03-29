package com.jaivyroy.hospotalManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id  ;

    @NotNull
    @Column(unique = true , length = 50 )
    private  String name  ;

    @OneToOne
    private Doctor head_doctor ;

    @ManyToMany
    private Set<Doctor> doctorset = new HashSet<>() ;
}
