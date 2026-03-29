package com.jaivyroy.hospotalManagement.repositoy;

import com.jaivyroy.hospotalManagement.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    Insurance findFirstBy();

}