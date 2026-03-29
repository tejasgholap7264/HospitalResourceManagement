package com.jaivyroy.hospotalManagement.service.Insurance;


import com.jaivyroy.hospotalManagement.dto.PatientDto;
import com.jaivyroy.hospotalManagement.entity.Insurance;
import com.jaivyroy.hospotalManagement.entity.Patient;
import com.jaivyroy.hospotalManagement.repositoy.InsuranceRepository;
import com.jaivyroy.hospotalManagement.repositoy.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class InsurancePatientService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PatientDto assignInsuranceToPatient(Insurance insurance, long id) {

        // 1. Find patient
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        // 2. Set relationship
        patient.setInsurance(insurance);
        insurance.setPatient(patient);

        // 3. Save both (IMPORTANT for transaction)
        insuranceRepository.save(insurance);
        patientRepository.save(patient);

        // 4. Return DTO
        return modelMapper.map(patient, PatientDto.class);
    }
}