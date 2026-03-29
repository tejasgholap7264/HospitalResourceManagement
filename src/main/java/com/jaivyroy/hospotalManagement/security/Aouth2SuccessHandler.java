package com.jaivyroy.hospotalManagement.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaivyroy.hospotalManagement.dto.LoginResponceDto.LoginResponceDto;
import com.jaivyroy.hospotalManagement.service.AuthService.AuthSercive;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Aouth2SuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();
        String registrationId = token.getAuthorizedClientRegistrationId();

        log.info("🔐 OAuth2 Login Success :: Provider = {}", registrationId);
        log.info("👤 OAuth2 User Attributes = {}", oAuth2User.getAttributes());

        AuthSercive authSercive = applicationContext.getBean(AuthSercive.class);

        // Save user + generate JWT
        ResponseEntity<LoginResponceDto> loginResponse =
                authSercive.HandleOauth2LoginRequest(oAuth2User, registrationId);

        // Return JWT to frontend as JSON
        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                objectMapper.writeValueAsString(loginResponse.getBody())
        );

        log.info("🎉 OAuth2 login completed. JWT sent to frontend.");
    }


}