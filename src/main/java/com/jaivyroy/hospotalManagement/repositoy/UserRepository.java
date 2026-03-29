package com.jaivyroy.hospotalManagement.repositoy;

import com.jaivyroy.hospotalManagement.entity.Type.AuthproviderType;
import com.jaivyroy.hospotalManagement.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<user, Long> {

    Optional<user> findByUsername(String username);

    Optional<user> findByProviderIdAndAuthproviderType(String providerId, AuthproviderType authproviderType);

//    user findByEmail(String email);
}