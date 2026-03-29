package com.jaivyroy.hospotalManagement.dto.LoginResponceDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponceDto {
    String jwt ;
   long userId  ;


}