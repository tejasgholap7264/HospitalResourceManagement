package com.jaivyroy.hospotalManagement.controller;

import com.jaivyroy.hospotalManagement.dto.AppointmentDto.appointmentDto;
import com.jaivyroy.hospotalManagement.service.AppointMent.AppointMentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointMentController {

    @Autowired
    private final AppointMentService appointMentService  ;

    public AppointMentController(AppointMentService appointMentService) {
        this.appointMentService = appointMentService;
    }

    // find all the appointment with doctor name  , apatient name , data , desses ,
             @GetMapping("/all")
    public ResponseEntity <List<appointmentDto>>aapointmentlist()  {
        List<appointmentDto>appointmentDtoList  = appointMentService.getAllAppoitment() ;
        return ResponseEntity.ok(appointmentDtoList) ;
    }

}