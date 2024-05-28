package com.abello.ecommerce.ecommercereplica.model.dto.response;

public record CustomerResponseDTO(
        Long id,
        String name,
        String lastname,
        String cellphone,
        String email,
        String username
) {
}
