package com.abello.ecommerce.ecommercereplica.service.registration;


import com.abello.ecommerce.ecommercereplica.model.ConfirmationToken;
import com.abello.ecommerce.ecommercereplica.repository.ConfirmationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImp implements IConfirmationToken {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Transactional
    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        ConfirmationToken c = confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElse(null);
        if (confirmationToken == null) {
            throw new IllegalStateException("The token doesn't exist");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
    }
}