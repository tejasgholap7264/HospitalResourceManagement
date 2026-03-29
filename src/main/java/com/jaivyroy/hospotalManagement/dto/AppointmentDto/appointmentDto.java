package com.jaivyroy.hospotalManagement.dto.AppointmentDto;


import com.jaivyroy.hospotalManagement.dto.DoctorDto.doctorDto;
import com.jaivyroy.hospotalManagement.dto.PatientDto;
//import com.jaivyroy.hospotalManagement.entity.Gender;
import lombok.Data;

@Data
public class appointmentDto {
  private PatientDto patientDto ;
  private doctorDto doctorDto  ;

}