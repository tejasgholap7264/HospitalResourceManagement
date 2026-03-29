package com.jaivyroy.hospotalManagement.service.AuthService;

import com.jaivyroy.hospotalManagement.dto.LoginResponceDto.LoginResponceDto;
import com.jaivyroy.hospotalManagement.dto.LoginResponceDto.LoginrequestDto;
import com.jaivyroy.hospotalManagement.dto.SIngUpDto.SingRequestDto;
import com.jaivyroy.hospotalManagement.dto.SIngUpDto.SingupResponceDto;
import com.jaivyroy.hospotalManagement.entity.Patient;
import com.jaivyroy.hospotalManagement.entity.Type.AuthproviderType;
import com.jaivyroy.hospotalManagement.entity.Type.RoolTYPE;
import com.jaivyroy.hospotalManagement.entity.user;
import com.jaivyroy.hospotalManagement.repositoy.PatientRepository;
import com.jaivyroy.hospotalManagement.repositoy.UserRepository;
import com.jaivyroy.hospotalManagement.security.Authutill;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceimp implements AuthSercive {

    private final AuthenticationManager authenticationManager;
    private final Authutill authutill;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    @Override
    public LoginResponceDto login(LoginrequestDto loginrequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginrequestDto.getUsername(),
                        loginrequestDto.getPassword()
                )
        );

        user users = (user) authentication.getPrincipal();
        String token = authutill.generateAccessToken(users);

        return new LoginResponceDto(token, users.getId());
    }

    // sign up with controller through email
    @Override
    public SingupResponceDto singup(SingRequestDto singRequestDto) {
        Optional<user> existingUser = userRepository.findByUsername(singRequestDto.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException(
                    "User already exists with username: " + singRequestDto.getUsername()
            );
        }
        user users = user.builder()
                .username(singRequestDto.getUsername())
                .password(passwordEncoder.encode(singRequestDto.getPassword()))
                .providerId(null)
                .authproviderType(AuthproviderType.EMAIL)
                .rooltype(singRequestDto.getRoolTYPES())
                .build();

        users = userRepository.save(users);

        // create patient entry
        Patient patient = Patient.builder()
                .name(singRequestDto.getName())
                .email(singRequestDto.getUsername())
                .users(users)
                .build();

        patientRepository.save(patient);

        return modelMapper.map(users, SingupResponceDto.class);
    }

    // internal signup used elsewhere (kept, but not used for OAuth flow directly)
    public user singupinteranl(SingRequestDto singRequestDto, AuthproviderType authproviderType, String ProviderId) {
        Optional<user> existingUser = userRepository.findByUsername(singRequestDto.getUsername());
        if (existingUser.isPresent()) throw new IllegalArgumentException("User already exist ");

        user users = user.builder()
                .username(singRequestDto.getUsername())
                .providerId(ProviderId)
                .rooltype(singRequestDto.getRoolTYPES())
                .authproviderType(authproviderType)
                .build();

        if (authproviderType == AuthproviderType.EMAIL) {
            users.setPassword(passwordEncoder.encode(singRequestDto.getPassword()));
        }
        users = userRepository.save(users);
        // create patient
        Patient patient = Patient.builder()
//                (ProviderId)
                .name(singRequestDto.getName())
                .email(singRequestDto.getUsername())
//                .rooltype(singRequestDto.getRoolTYPES())
                .users(users)
                .build();

        patientRepository.save(patient);

        return users;
    }

    /**
     * Handles OAuth2 login flow (called from SuccessHandler).
     * - Safely extract providerId, email, login, name.
     * - Build a guaranteed-non-null username using fallbacks.
     * - Reuse existing users by providerId OR by email.
     * - Save user (with a non-null encoded password) and associated Patient if created new.
     */
    @Override
    @Transactional
    public ResponseEntity<LoginResponceDto> HandleOauth2LoginRequest(
            OAuth2User oAuth2User, String registrationId) {

        log.info("HandleOauth2LoginRequest started. provider={}", registrationId);

        AuthproviderType provider = authutill.getProviderIdFromResitractionId(registrationId);

        // --- 1) Extract provider-specific ID ---
        String providerId = authutill.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        // --- 2) Extract common fields ---
        String email = oAuth2User.getAttribute("email");      // Google: OK, GitHub: mostly null
        String name  = oAuth2User.getAttribute("name");       // Google: OK, GitHub: sometimes null
        String githubLogin = oAuth2User.getAttribute("login"); // GitHub always gives login

        // --- 3) SAFE USERNAME LOGIC ---
        String username;

        if (email != null) {                         // Google or GitHub with public email
            username = email;
        } else if (githubLogin != null) {            // GitHub safe fallback
            username = "github_" + "@oauth.fake";
        } else {                                      // Worst-case fallback
            username = registrationId + "_" + providerId;
        }

        log.info("OAuth user resolved -> username={}, providerId={}, name={}",
                username, providerId, name);

        // --- 4) Check user exists by provider ---
        Optional<user> existingUser =
                userRepository.findByProviderIdAndAuthproviderType(providerId, provider);

        user savedUser;

        if (existingUser.isPresent()) {
            savedUser = existingUser.get();
        } else {

            // --- 5) Check if email already used by normal signup ---
            Optional<user> byEmail = userRepository.findByUsername(username);

            if (byEmail.isPresent()) {
                savedUser = byEmail.get();
            } else {

                // --- 6) Create totally new user ---
                savedUser = user.builder()
                        .username(username)
                        .providerId(providerId)
                        .authproviderType(provider)
                        .password("OAUTH_USER") // ye sirf ek dummy password hai jo ki database  ke contains  ko maintain kar  hia
                        // agr user ne oauth2 se login kiya hi  to usko kabhi bhi password ki jarurt nhi hoti hia log in kartne  ke liye
                        // agr fir bhi vo chahta hia   ki agr se password set kre to alg se api bnana parega

                        .rooltype(Set.of(RoolTYPE.PATIENT))
                        .build();

                savedUser = userRepository.save(savedUser);
                log.info("Created new OAuth user id={}", savedUser.getId());

                // --- 7) Create Patient record (with safe email value) ---
                String patientEmail = (email != null) ? email : (username + "@oauth.fake"); // at the api responce cheak  if(email.endWith("@oauth.fake") ) return null  ;

                Patient patient = Patient.builder()
                        .name(name != null ? name : username)
                        .email(patientEmail)
                        .users(savedUser)
                        .build();

                patientRepository.save(patient);
                log.info("Created patient for user id={}", savedUser.getId());
            }
        }

        log.info("OAuth2 handling completed for username={}, userId={}",
                savedUser.getUsername(), savedUser.getId());

        // --- 8) Returning JWT after success ---
        String token = authutill.generateAccessToken(savedUser);

        return ResponseEntity.ok(new LoginResponceDto(token, savedUser.getId()));
    }


    // helper to avoid ClassCast / NPE surprises
    private String safeStr(Object o) {
        if (o == null) return null;
        return o.toString();
    }
}