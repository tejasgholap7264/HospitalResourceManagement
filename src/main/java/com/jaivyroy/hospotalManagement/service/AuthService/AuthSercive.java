package com.jaivyroy.hospotalManagement.service.AuthService;



import com.jaivyroy.hospotalManagement.dto.LoginResponceDto.LoginResponceDto;
import com.jaivyroy.hospotalManagement.dto.LoginResponceDto.LoginrequestDto;
import com.jaivyroy.hospotalManagement.dto.SIngUpDto.SingRequestDto;
import com.jaivyroy.hospotalManagement.dto.SIngUpDto.SingupResponceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service

public interface AuthSercive {

    LoginResponceDto login(LoginrequestDto loginrequestDto);

    SingupResponceDto singup(SingRequestDto singRequestDto);

    ResponseEntity<LoginResponceDto> HandleOauth2LoginRequest(OAuth2User oAuth2User, String registractinoId);
}