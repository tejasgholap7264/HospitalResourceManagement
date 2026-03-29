package com.jaivyroy.hospotalManagement.repositoy;

import com.jaivyroy.hospotalManagement.entity.Patient;
import com.jaivyroy.hospotalManagement.entity.Type.BloodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository <Patient, Long > {



    Optional<Patient> getByNameIgnoreCase(String name);

    Optional<Patient> getByPhoneNumber(Long mobile);

    Optional<Patient> getByNameOrEmail(String name, String email);

    Optional<Patient> getByNameContainsIgnoreCase(String namelatter);
    //  PatientDto getByName(String name);
    // Corrected custom query method
    @Query("SELECT p FROM Patient p WHERE p.bloodGrp = :bloodType")
    Optional<Patient> findByBloodGrp(@Param("bloodType") BloodType bloodType);

//    @Query ("Select n FROM patient n Wherer n.phoneNumber = : number ")
//    Optional<Patient>findByphoneNumber(@Param("number") Long phoneNumber ) ;
@Query("SELECT n FROM Patient n WHERE n.phoneNumber = :number")
Optional<Patient> findByPhoneNumber(@Param("number") Long number);



    // FIXED: correct JPQL for grouping all patients by blood group
    @Query("SELECT p.bloodGrp AS bloodGroup, COUNT(p) AS count FROM Patient p GROUP BY p.bloodGrp")
    List<Object[]> getPatientsGroupedByBloodGroup();

    @Query ("Select p.name AS PatientName , p.age As PatientAge From Patient p ")
    List<Object[]> getAllPatientsNameOrAge();

//    @Query("select * from patient_data")
@Query(value = "SELECT * FROM patient_data", nativeQuery = true)
    List<Object[]> getAllPatient();

// update the name  with the id
    @Modifying    // when the update in the  db when use this annotaion along with in the
    // serive use @Tracnsactoin
    @Query("UPDATE  Patient p  SET p.name =:name  WHERE p.id = :id")
    int updateName(@Param("id") long id, @Param("name") String name) ;
//@Modifying
//@Query("UPDATE Patient p SET p.name = :name WHERE p.id = :id")
//int updateName(@Param("id") long id, @Param("name") String name);
}