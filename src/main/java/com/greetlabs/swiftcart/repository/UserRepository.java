package com.greetlabs.swiftcart.repository;

import com.greetlabs.swiftcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserEmail(String email);
    Optional<User> findByUserMobile(Long email);
//    Optional<Boolean> findByUserEmail(String email);
}
