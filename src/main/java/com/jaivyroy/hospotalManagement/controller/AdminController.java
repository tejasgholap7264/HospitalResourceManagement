package com.jaivyroy.hospotalManagement.controller;

import com.jaivyroy.hospotalManagement.dto.PatientDto;
import com.jaivyroy.hospotalManagement.entity.Patient;
import com.jaivyroy.hospotalManagement.entity.user;
import com.jaivyroy.hospotalManagement.repositoy.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientRepository patientRepository;

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        // Logged-in user (JWT)
        user loggedInUser = (user) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        System.out.println("Admin logged in: " + loggedInUser.getUsername());

        // Pagination
        Pageable pageable = PageRequest.of(page, size);

        List<PatientDto> patients = patientRepository.findAll(pageable)
                .stream()
                .map(this::convertToDto)
                .toList();

        return ResponseEntity.ok(patients);
    }

    // Convert Patient Entity → DTO
    private PatientDto convertToDto(Patient p) {
        return new PatientDto(
                p.getId(),
                p.getName(),
                p.getEmail(),
                p.getPhoneNumber(),
                p.getGender(),
                p.getDisease(),
                p.getRoomNumber(),
                p.getBloodGrp()
        );
    }
}