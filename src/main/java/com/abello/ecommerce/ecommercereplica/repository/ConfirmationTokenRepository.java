package com.abello.ecommerce.ecommercereplica.repository;


import com.abello.ecommerce.ecommercereplica.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
//TODO: make the method after make and test the others repositories
    Optional<ConfirmationToken> findByToken(String token);
}
