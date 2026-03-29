package com.jaivyroy.hospotalManagement.security;

import com.jaivyroy.hospotalManagement.entity.Type.AuthproviderType;
import com.jaivyroy.hospotalManagement.entity.user;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class Authutill {

    @Value("${jwt.secretKey}")
    private String jwtSecretkey;

    private final JwtService jwtService; // used only for generation if desired

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretkey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(user us) {
        // Prefer Delegating to JwtService if you have complex claims
        return jwtService.generateAccessToken(us);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiry = extractAllClaims(token).getExpiration();
        return expiry.before(new Date());
    }

    private Claims extractAllClaims(String token) {
        // Use io.jsonwebtoken parserBuilder -> parseClaimsJws
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Map registrationId to enum
    public AuthproviderType getProviderIdFromResitractionId(String registrationId) {
        if (registrationId == null) return AuthproviderType.EMAIL;
        switch (registrationId.toLowerCase()) {
            case "google": return AuthproviderType.GOOGLE;
            case "github": return AuthproviderType.GITHUB;
            case "facebook": return AuthproviderType.FECBOOK;
            case "twitter": return AuthproviderType.TWITTER;
            case "email": return AuthproviderType.EMAIL;
            default: return AuthproviderType.OTHER;
        }
    }

    // Determine provider-specific id from OAuth2User
    public String determineProviderIdFromOAuth2User(OAuth2User oAuth2User, String registrationId) {
        if (registrationId == null) throw new IllegalArgumentException("registrationId is null");
        String id;
        switch (registrationId.toLowerCase()) {
            case "google":
                id = oAuth2User.getAttribute("sub");
                break;
            case "github":
                Object raw = oAuth2User.getAttribute("id");
                id = raw == null ? null : raw.toString();
                break;
            default:
                id = oAuth2User.getName();
        }
        if (id == null || id.isBlank()) {
            log.warn("provider id not found for registrationId={}", registrationId);
            id = oAuth2User.getName();
        }
        return id;
    }

    public String determineUserNameFromOauth2User(OAuth2User oAuth2User, String registrationId, String providerId) {
        String email = oAuth2User.getAttribute("email");
        if (email != null && !email.isBlank()) return email;
        // fallback to providerId
        return registrationId + "_" + providerId;
    }
}