package com.jaivyroy.hospotalManagement.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // ONLY LOGGING, NO SAVING
        log.info("OAuth2 Attributes = {}", oAuth2User.getAttributes());

        return oAuth2User;
    }
}



//package com.jaivyroy.hospotalManagement.security;
//
//import com.jaivyroy.hospotalManagement.entity.Type.AuthproviderType;
//import com.jaivyroy.hospotalManagement.entity.user;
//import com.jaivyroy.hospotalManagement.repositoy.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//
//        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
//
//        String email = oAuth2User.getAttribute("email");
//        String name = oAuth2User.getAttribute("name");
//
//        // If user does not exist → Save it
//        userRepository.findByUsername(email)
//                .orElseGet(() -> {
//                    user newUser = new user();
//                    newUser.setUsername(email);
////                    newUser.setName(name);
//                    newUser.setAuthproviderType(AuthproviderType.GOOGLE);
//                    System.out.println("USer save Not save in the database ");
//                    return userRepository.save(newUser) ;
////                    System.out.println("User save in the data base ");
//                });
//        System.out.println("User save in the data base ");
//        return oAuth2User;
//    }
//}