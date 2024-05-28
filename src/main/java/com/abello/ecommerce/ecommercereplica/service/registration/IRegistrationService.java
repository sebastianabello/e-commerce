package com.abello.ecommerce.ecommercereplica.service.registration;


import com.abello.ecommerce.ecommercereplica.model.dto.request.CustomerRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.AuthenticationResponseDTO;

public interface IRegistrationService {
    public AuthenticationResponseDTO save(CustomerRequestDTO requestDTO) throws IllegalAccessException;
    public String confirmToken(String token);
}
