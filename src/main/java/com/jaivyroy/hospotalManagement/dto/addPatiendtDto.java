package com.jaivyroy.hospotalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class addPatiendtDto {
     private  String name  ;
     private String email ;
     private Long mobile  ;
     private String Gender  ;
    private String  blood_grp ;
    private LocalDate birthdate;

}