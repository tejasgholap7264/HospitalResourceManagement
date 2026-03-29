package com.jaivyroy.hospotalManagement.dto;

import com.jaivyroy.hospotalManagement.entity.Type.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientDto {

    private Long id;
    private String name;
    private String email;
    private Long phoneNumber;
    private Gender gender;
    private String disease;
    private int roomNumber;
    private String bloodGrp;

    // Small DTO constructor when returning limited data
    public PatientDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}