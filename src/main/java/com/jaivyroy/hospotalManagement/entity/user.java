package com.jaivyroy.hospotalManagement.entity;

import com.jaivyroy.hospotalManagement.entity.Type.AuthproviderType;
import com.jaivyroy.hospotalManagement.entity.Type.RoolTYPE;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
 @Builder
public class user implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

//    @Column(nullable = false)
    private String password;


    private String providerId ;

    @Enumerated(EnumType.STRING)
    private AuthproviderType authproviderType ;

     @ElementCollection(fetch = FetchType.EAGER)
     @Enumerated(EnumType.STRING)
    Set<RoolTYPE>rooltype = new HashSet<>() ;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rooltype.stream()
                .map(role -> new SimpleGrantedAuthority("Role_"+ role.name()))
                .collect(Collectors.toSet());
    }

    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    //isAccountNonExpired()
    //Has the account expired?
    //You are NOT using expiration → always active
    @Override public boolean isAccountNonExpired() { return true; }
    //isAccountNonLocked()
    //Is user locked due to too many attempts?
    //You are NOT using account lock
    @Override public boolean isAccountNonLocked() { return true; }
    //isCredentialsNonExpired()
    //Has password expired?
    //You are NOT using password expiry
    @Override public boolean isCredentialsNonExpired() { return true; }
        //isEnabled()	Is user active?	You are NOT maintaining enable/disable flag

    @Override public boolean isEnabled() { return true; }


}