package com.jaivyroy.hospotalManagement.repositoy;

import com.jaivyroy.hospotalManagement.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}