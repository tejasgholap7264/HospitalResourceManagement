package com.jaivyroy.hospotalManagement.controller;

import com.jaivyroy.hospotalManagement.dto.PatientDto;
import com.jaivyroy.hospotalManagement.entity.user;
import com.jaivyroy.hospotalManagement.repositoy.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final AppointmentRepository appointmentRepository;

    @GetMapping("/appointments")
    public ResponseEntity<List<PatientDto>> getAllAppointments() {

        user loggedInDoctor = (user) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Long doctorId = loggedInDoctor.getId();

        List<PatientDto> appointments =
                appointmentRepository.getAllAppointmentsOfDoctor(doctorId);

        return ResponseEntity.ok(appointments);
    }
}