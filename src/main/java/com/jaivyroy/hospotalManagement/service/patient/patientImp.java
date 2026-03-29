package com.jaivyroy.hospotalManagement.service.patient;


import com.jaivyroy.hospotalManagement.dto.BloodGroupCountDto;
import com.jaivyroy.hospotalManagement.dto.GetNameAgeDto;
import com.jaivyroy.hospotalManagement.dto.PatientDto;
import com.jaivyroy.hospotalManagement.dto.addPatiendtDto;
import com.jaivyroy.hospotalManagement.entity.Patient;
import com.jaivyroy.hospotalManagement.entity.Type.Gender;
import com.jaivyroy.hospotalManagement.repositoy.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class patientImp implements PatientService {

    //  in the service we will implement the  bussiness logic data  forform the database
    private final PatientRepository pateintrepository  ;
    // model mapper
    private final ModelMapper modelMapper;



    @Override
    @Cacheable(cacheNames = "Patients", key = "'allPatients'")
    public List<PatientDto> getAllPatients() {
        List< Patient> patients  = pateintrepository.findAll() ;

        // Corrected modelMapper.map usage
        return patients.stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))  // patient -> modelMapper.map(patient, PatientDto.class)
                .collect(Collectors.toList());
    }

    @Override

    public addPatiendtDto addPatient(Patient patient) {
        final Patient savepatient  = pateintrepository.save(patient);
        return  modelMapper.map(patient , addPatiendtDto.class) ;

    }

    @Override
    public PatientDto getById(long id) throws Exception {
        Optional<Patient> optionalPatient = pateintrepository.findById(id);

        // Return the mapped PatientDto if the patient is found, otherwise throw an exception
        return optionalPatient
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .orElseThrow(() -> new Exception("Patient not found with id: " + id));
    }

    @Override
    public PatientDto getByName(String name) {
        Optional<Patient> optionalPatient = pateintrepository.getByNameIgnoreCase(name);  // Corrected the repository method call

        return optionalPatient
                .map(patient -> modelMapper.map(patient, PatientDto.class))  // Correctly map the patient to PatientDto
                .orElseThrow(() -> new RuntimeException("Patient not found with name: " + name));  // Throw exception if not found
    }

    @Override
    public PatientDto getByMobileNumber(Long mobile) {
        Optional<Patient>optionalPatient = pateintrepository.getByPhoneNumber(mobile) ;

        return optionalPatient
                .map(patient -> modelMapper.map(patient , PatientDto.class))
                .orElseThrow( () ->new RuntimeException("Patient not found ")) ;
    }

    @Override
    public PatientDto getByNameOrEmail(String name, String email) {
        Optional<Patient>optionalPatient = pateintrepository.getByNameOrEmail(name , email ) ;
        return optionalPatient
                .map(patient -> modelMapper.map(patient , PatientDto.class))
                .orElseThrow( () ->new RuntimeException("Patent not foudd ")) ;
    }

    @Override
    public PatientDto findByNameContains(String namelatter) {
        Optional<Patient>optionalPatient = pateintrepository.getByNameContainsIgnoreCase(namelatter) ;
        return optionalPatient
                .map(patient -> modelMapper.map(patient , PatientDto.class))
                .orElseThrow(()->new RuntimeException("That Type No any patien found "));
    }

    @Override
    public PatientDto getphoneNumber(Long phoneNumber) {
        Optional<Patient>optionalPatient = pateintrepository.getByPhoneNumber(phoneNumber) ;
        return optionalPatient
                .map(patient -> modelMapper.map(patient , PatientDto.class))
                .orElseThrow( ()->new RuntimeException("There is not any  patient admit ") ) ;
    }

    //  FIXED: new group-by logic
    @Override
    public List<BloodGroupCountDto> getAllPatientsGroupedByBloodGroup() {
        List<Object[]> results = pateintrepository.getPatientsGroupedByBloodGroup();

        if (results.isEmpty()) {
            throw new RuntimeException("No patients found in the database");
        }

        return results.stream()
                .map(obj -> new BloodGroupCountDto((String) obj[0], (Long) obj[1]))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetNameAgeDto> getAllPatientsNameOrAge() {
        List<Object[]>result = pateintrepository.getAllPatientsNameOrAge() ;
        if(result.isEmpty()) {
            throw new RuntimeException("No any patient are in  are admit in the hospital" ) ;
        }
        return result.stream()
                .map(obj->new GetNameAgeDto( (String) obj[0] , (int) obj[1]) )
                .collect(Collectors.toList());
    }

//    @Override
//    public List<PatientDto> getAllPatient() {
//    List<Patient>rest = pateintrepository.getAllPatient () ;
//    if(rest.isEmpty()) {
//        throw new RuntimeException("No any patinet are admit in the hospital ") ;
//    }
//     return rest.stream()
//             .map(patient-> modelMapper.map(patient ,PatientDto.class))
//             .collect(Collectors.toList() ) ;
//    }

    @Override
    public List<PatientDto> getAllPatient() {
        List<Object[]> rest = pateintrepository.getAllPatient();

        if (rest.isEmpty()) {
            throw new RuntimeException("No patients admitted in the hospital");
        }

        return rest.stream()
                .map(obj -> {
                    PatientDto dto = new PatientDto();
                    dto.setName((String) obj[0]);
                    dto.setEmail((String) obj[1]);
                    dto.setPhoneNumber(((Number) obj[2]).longValue());
                    dto.setGender((Gender) obj[3]); // if Gender is stored as enum
                    dto.setDisease((String) obj[4]);
                    dto.setRoomNumber(Integer.parseInt((String) obj[5]));
                    dto.setBloodGrp((String) obj[6]);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    // @Transactional
    @Transactional// need for the @modify annotation
    public PatientDto upDateName(long id, String name) {
        int rowupdate = pateintrepository.updateName(id , name ) ;
        if(rowupdate == 0 ) {
            throw  new RuntimeException("No patient found witht the id " + id ) ;
        }
        Patient updatedPatient = pateintrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found after update"));

        return modelMapper.map(updatedPatient , PatientDto.class ) ;
    }


}