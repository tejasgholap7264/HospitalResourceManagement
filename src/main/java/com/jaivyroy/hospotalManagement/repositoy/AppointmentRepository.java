package com.jaivyroy.hospotalManagement.repositoy;

import com.jaivyroy.hospotalManagement.dto.PatientDto;
import com.jaivyroy.hospotalManagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
        SELECT new com.jaivyroy.hospotalManagement.dto.PatientDto(
            p.id, 
            p.name, 
            p.email
        )
        FROM Appointment a
        JOIN a.patient p
        WHERE a.doctor.id = :doctorId
    """)
    List<PatientDto> getAllAppointmentsOfDoctor(@Param("doctorId") Long doctorId);
}