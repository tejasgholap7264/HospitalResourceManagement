package com.jaivyroy.hospotalManagement.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id  ;

    @Column(nullable = false )
    @CreationTimestamp
    private LocalDateTime AppointmentDataTime  ;

    @NotNull
    @Column(length =  500)
    private String Reason  ;


    @ManyToOne
    @JoinColumn(name ="patient_id" , nullable = false )
    private Patient patient  ;    //  patien  is required  or not nullable
    // mtlb samjho ki bina patient ke appointment ka   koi  mtlb  hi nhi banhat hai

    @ManyToOne
    @JoinColumn(name="doctor_id" , nullable = false)
    private  Doctor doctor ;
}
