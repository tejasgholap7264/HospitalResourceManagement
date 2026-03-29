package com.jaivyroy.hospotalManagement.repositoy;

import com.jaivyroy.hospotalManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}