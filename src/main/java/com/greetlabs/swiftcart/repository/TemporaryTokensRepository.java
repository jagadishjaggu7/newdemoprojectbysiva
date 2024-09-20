package com.greetlabs.swiftcart.repository;

import com.greetlabs.swiftcart.entity.TemporaryTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporaryTokensRepository extends JpaRepository<TemporaryTokens,Long> {
    Optional<TemporaryTokens> findByUserEmail(String userEmail);
    Boolean existsByTemporaryToken(String temporaryToken);

    Optional<TemporaryTokens> findByTemporaryToken(String token);
}
