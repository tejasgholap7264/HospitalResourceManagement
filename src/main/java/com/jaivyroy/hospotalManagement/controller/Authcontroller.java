package com.jaivyroy.hospotalManagement.controller;

import com.jaivyroy.hospotalManagement.dto.LoginResponceDto.LoginResponceDto;
import com.jaivyroy.hospotalManagement.dto.LoginResponceDto.LoginrequestDto;
import com.jaivyroy.hospotalManagement.dto.SIngUpDto.SingRequestDto;
import com.jaivyroy.hospotalManagement.dto.SIngUpDto.SingupResponceDto;
import com.jaivyroy.hospotalManagement.service.AuthService.AuthSercive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Authcontroller {

    private final AuthSercive authSercive;

    //===============================
    // LOGIN API  (POST /auth/login)
    //===============================
    @PostMapping("/login")
    public ResponseEntity<LoginResponceDto> login(@RequestBody LoginrequestDto loginrequestDto) {
        LoginResponceDto response = authSercive.login(loginrequestDto);
        log.info("login controller reached");


        return ResponseEntity.ok(response);
    }

    //=================================
    // SIGNUP API  (POST /auth/signup)
    //=================================

    @PostMapping("/signup")
    public ResponseEntity<SingupResponceDto> signup(@RequestBody SingRequestDto singRequestDto) {
        SingupResponceDto response = authSercive.singup(singRequestDto);
        log.info("Signup controller reached");
        return ResponseEntity.ok(response);
    }
}