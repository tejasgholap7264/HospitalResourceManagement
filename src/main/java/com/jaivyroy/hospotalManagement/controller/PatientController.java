package com.jaivyroy.hospotalManagement.controller;

import com.jaivyroy.hospotalManagement.dto.BloodGroupCountDto;
import com.jaivyroy.hospotalManagement.dto.GetNameAgeDto;
import com.jaivyroy.hospotalManagement.dto.PatientDto;
import com.jaivyroy.hospotalManagement.dto.addPatiendtDto;
import com.jaivyroy.hospotalManagement.entity.Patient;
import com.jaivyroy.hospotalManagement.service.patient.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/patients")
@RestController
public class PatientController {

    @Autowired
    private  final PatientService patientService ;
    // constructor for the patientservice
    public PatientController(PatientService patientService) {

        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        // Get all patients from the service and return a ResponseEntity
        List<PatientDto> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);  // Return ResponseEntity with status OK
    }

    @PostMapping("/admit")
    public ResponseEntity <addPatiendtDto> addPatient(@RequestBody @Valid Patient patient ) {

        addPatiendtDto addpatiendtDto ;
        try{
            addpatiendtDto =  patientService.addPatient( patient ) ;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  ResponseEntity.ok(addpatiendtDto) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getById(@PathVariable("id") long id) {
        try {
            // Assuming patientService.getById throws an exception if no patient is found
            PatientDto patientDto = patientService.getById(id);
            return ResponseEntity.ok(patientDto);
        } catch (Exception e) {
            // Handle case where patient is not found, you can create a custom exception like PatientNotFoundException
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // You can return a custom message or null here
        }
            // You can log the exception and return a more user-friendly message

    }


    // ✅ GET by Name
    @GetMapping("/name/{name}")
    public ResponseEntity<PatientDto> getByName(@PathVariable("name") String name) {
        try {
            PatientDto patientDto = patientService.getByName(name);
            return ResponseEntity.ok(patientDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<PatientDto>getByMobileNumber(@PathVariable("mobile") Long mobile  ) {
        try{
             PatientDto patientDto   = patientService.getByMobileNumber(mobile) ;
           return   ResponseEntity.ok(patientDto) ;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ;
        }
    }

    @GetMapping("/name/email/{name}/{email}")
    public ResponseEntity<PatientDto>getBynameoremail (@PathVariable("name") String name ,@PathVariable("email") String email ) {
        try{
            PatientDto patientDto = patientService.getByNameOrEmail(name , email ) ;
            return ResponseEntity.ok(patientDto) ;
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ;
        }
    }

    @GetMapping("/name/contains/{namelatter}")
    public ResponseEntity<PatientDto> getByNameLatter(@PathVariable("namelatter") String namelatter) {
        try {
            PatientDto patientDto = patientService.findByNameContains(namelatter);
            return ResponseEntity.ok(patientDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/phonenumber/{number}")
    public ResponseEntity<PatientDto> getbyPhoneNumber (@PathVariable ("number") long number) {
        try{
            PatientDto patientDto = patientService.getphoneNumber(number) ;
            return ResponseEntity.ok(patientDto) ;
        }catch (Exception e ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ;
        }
    }
    // all the patine from  the specific bloogrp

    @GetMapping("/groupby-bloodgrp")
    public ResponseEntity<List<BloodGroupCountDto>> getAllPatientsGroupedByBloodGroup() {
        try {
            List<BloodGroupCountDto> groupedData = patientService.getAllPatientsGroupedByBloodGroup();
            return ResponseEntity.ok(groupedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/getname-getage")
    public ResponseEntity <List<GetNameAgeDto>> getAllPatientsNameOrAge() {
        try{
            List<GetNameAgeDto>groupNameOragr = patientService.getAllPatientsNameOrAge() ;
             return ResponseEntity.ok( groupNameOragr) ;
        }catch (Exception e ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ;
        }
    }
    @GetMapping("/allPatient")
    public ResponseEntity<List<PatientDto>> getAllPatient() {
        try{
            List<PatientDto>allPatient = patientService.getAllPatient() ;
          return ResponseEntity.ok(allPatient);
            //return allPatient.get(ResponseEntity::ok) ;
        }catch (Exception  e ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PutMapping("/{id}/name/{name}")
    public ResponseEntity <PatientDto>updateName(
            @PathVariable("name") String name ,
            @PathVariable("id") long id ) {
        try{
            PatientDto patientDto = patientService.upDateName(id , name ) ;
            return  ResponseEntity.ok(patientDto) ;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

     }

}