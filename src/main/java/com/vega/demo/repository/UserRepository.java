package com.vega.demo.repository;

import com.vega.demo.domain.UserSpringIt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserSpringIt, Long> {
    Optional<UserSpringIt> findByEmail(String email);

    Optional<UserSpringIt> findByEmailAndActivationCode(String email, String activationCode);
}
