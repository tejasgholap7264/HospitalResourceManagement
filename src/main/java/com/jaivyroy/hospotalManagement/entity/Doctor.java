package com.jaivyroy.hospotalManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id  ;

    @NotNull
    @Column(length = 100)
    private String name ;

//    @NotNull
//    bcz when the docter will be sing up than it will be treact as like patient
    // but  rool of docter will be define by the   authorised deparment like as admin , orginer ]
    // where doctor will be hored
    // so that NotNull is comment
    @Column(length = 150 )
    private String Specilist  ;

    @NotNull
    @Email
    @Column(length = 100)
    private String email ;

    @CreationTimestamp
    private LocalDateTime createAt ;

   // here doctor is map bcz doctor has patient
     @OneToOne
     @MapsId
     private user  users ;

    @ManyToMany(mappedBy = "doctorset")
    private Set<Department>departmentSet = new HashSet<>() ;

    @OneToMany (mappedBy = "doctor")
    private Set<Appointment>appointmentSet = new HashSet<>() ;
}