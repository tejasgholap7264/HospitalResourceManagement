package com.jaivyroy.hospotalManagement.service.patient;

import com.jaivyroy.hospotalManagement.dto.BloodGroupCountDto;
import com.jaivyroy.hospotalManagement.dto.GetNameAgeDto;
import com.jaivyroy.hospotalManagement.dto.PatientDto;
import com.jaivyroy.hospotalManagement.dto.addPatiendtDto;
import com.jaivyroy.hospotalManagement.entity.Patient;

import jakarta.validation.Valid;

import java.util.List;

public interface PatientService {


    List<PatientDto> getAllPatients();

    addPatiendtDto addPatient(@Valid Patient patient);

    PatientDto getById(long id) throws Exception;

    // PatientDto getByNameIgnoreCase(String name);

    PatientDto getByName(String name);

    PatientDto getByMobileNumber(Long mobile);

    PatientDto getByNameOrEmail(String name, String email);

    PatientDto findByNameContains(String namelatter);
    PatientDto getphoneNumber (Long phoneNumber ) ;

    //  NEW: for group-by-blood-group endpoint
    List<BloodGroupCountDto> getAllPatientsGroupedByBloodGroup();

    List<GetNameAgeDto> getAllPatientsNameOrAge();

    List<PatientDto> getAllPatient();

    PatientDto upDateName(long id, String name);
}