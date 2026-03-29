package com.jaivyroy.hospotalManagement.service.AppointMent;
import com.jaivyroy.hospotalManagement.dto.AppointmentDto.appointmentDto;
import com.jaivyroy.hospotalManagement.dto.DoctorDto.doctorDto;
import com.jaivyroy.hospotalManagement.dto.PatientDto;
import com.jaivyroy.hospotalManagement.entity.Appointment;
import com.jaivyroy.hospotalManagement.entity.Doctor;
import com.jaivyroy.hospotalManagement.entity.Patient;
import com.jaivyroy.hospotalManagement.repositoy.AppointmentRepository;
import com.jaivyroy.hospotalManagement.repositoy.DoctorRepository;
import com.jaivyroy.hospotalManagement.repositoy.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointMentServiceimp implements AppointMentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    //  private final AppointmentRepository appointmentRepository ;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    // create a method to creat appoint
    @Transactional
    public Appointment createAppointMent(Appointment appointment, long docterId, long patientId) {
        // agr hme appointment chahiyt to phle to hmare pass me doctor hona chahiye or doctomer hona
        // chahiye esliye phle doctor or patient  ko le kar aao
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        Doctor doctor = doctorRepository.findById(docterId).orElseThrow();

        //jab doctor or patient aa gye hai to cheak kr loo ki  kahi appointment phle se to nhi na bna h hia

        if (appointment.getId() != 0) throw new IllegalArgumentException("Appointment shoube not re create ");

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        patient.getAppointmentList().add(appointment); //just for the maintain the bydirectinal consistancy
        return appointmentRepository.save(appointment);
    }

    // reassing the appointment
    public Appointment Reappointment(long appointmentId, long docterId) {

        Appointment appointment1 = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor fornewassingdoctor = doctorRepository.findById(docterId).orElseThrow();

        appointment1.setDoctor(fornewassingdoctor); //  this will be call the automatically update bcz it is durty now

        fornewassingdoctor.getAppointmentSet().add(appointment1); // just of bydirection  data maintain  consistancy
        return appointment1;

    }
// this is done using the mannual maping
    /*
@Override
public List<appointmentDto> getAllAppoitment() {
    return appointmentRepository.findAll()
            .stream()
            .map(appointment -> {
                appointmentDto dto = new appointmentDto();

                // Patient mapping
                Patient patient = appointment.getPatient();
                PatientDto patientDto = new PatientDto();
                patientDto.setName(patient.getName());
                patientDto.setEmail(patient.getEmail());
                patientDto.setPhoneNumber(patient.getPhoneNumber());
                patientDto.setGender(patient.getGender());
                patientDto.setDisease(patient.getDisease());
                patientDto.setGetRoomNumber(patient.getRoomNumber());
                patientDto.setBloodGrp(patient.getBloodGrp());
                dto.setPatientDto(patientDto);

                // Doctor mapping
                Doctor doctor = appointment.getDoctor();
                doctorDto doctorDto = new doctorDto();
                doctorDto.setId(doctor.getId());
                doctorDto.setName(doctor.getName());
                doctorDto.setSpecilist(doctor.getSpecilist());
                dto.setDoctorDto(doctorDto);

                return dto;
            })
            .collect(Collectors.toList());
}
*/
//

    @Override
    public List<appointmentDto> getAllAppoitment() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointment -> {
                    // Map patient
                    PatientDto patientDto = modelMapper.map(appointment.getPatient(), PatientDto.class);

                    // Map doctor
                    doctorDto doctorDto = modelMapper.map(appointment.getDoctor(), doctorDto.class);

                    // Map to appointmentDto
                    appointmentDto dto = new appointmentDto();
                    dto.setPatientDto(patientDto);
                    dto.setDoctorDto(doctorDto);

                    return dto;
                })
                .collect(Collectors.toList());
    }


}