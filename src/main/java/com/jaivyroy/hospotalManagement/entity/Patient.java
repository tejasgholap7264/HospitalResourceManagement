package com.jaivyroy.hospotalManagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jaivyroy.hospotalManagement.entity.Type.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "patient_data",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patent_email", columnNames = {"email"}),
                @UniqueConstraint(name = "unique_name_birthdate", columnNames = {"name", "birthdate"})
        } ,
        indexes = {
                @Index(name = "ind_patient_birthdate_email", columnList = "birthdate, email")
        }
)


public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name must not be blank and must have at least 2 characters
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    private  int age  ;
    // Date of birth must be not null and must be a past date
 //   @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    // Email must be a valid email
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    // Gender must not be blank and only allowed values (optional)
  //   @NotNull(message = "Gender is required")
    @Enumerated(EnumType.STRING)
   // @Pattern(regexp = "MALE|FEMALE|OTHER", message = "Gender must be Male, Female, or Other")
    private Gender gender;

   // @NotNull(message = "Mobile number is required")
    @Min(value = 1000000000L, message = "Phone number must be at least 10 digits")
    @Max(value = 9999999999L, message = "Phone number cannot exceed 10 digits")
    @Column(unique = true)
    private Long phoneNumber;

    @CreationTimestamp
     private LocalDateTime admitDateTime ;


    private String  bloodGrp ;
  //   @NotBlank (message = "Muster enter teh detail of the patient ")
  private  String Disease ;


   private  int roomNumber ;

   @OneToOne
   @MapsId  // there is two id in the pertien or user but these are same so that we want to store this id the only user not to patien
   // so that mapsid is  used
   private  user users ;

   @OneToOne( cascade = {CascadeType.MERGE  , CascadeType.PERSIST })
   @JoinColumn(name ="patient_insurence_id")
   private Insurance insurance  ; // owing side


    // orphon removela yha pe agr man loo ki patient hi delete ho gya hai to appoint ment kya hi kreaga
    // jb parent owner hi delete ho gya to apppoint bhi delte ho jana chahiye esliye orphoneremoveal == true user huua hia
    @OneToMany(mappedBy = "patient" , fetch= FetchType.LAZY , cascade = {CascadeType.REMOVE}  ,orphanRemoval = true )
    private List<Appointment> appointmentList = new ArrayList<>();

}