package com.abello.ecommerce.ecommercereplica.model.dto.request;

public record CustomerRequestEditDTO(
        String name,
        String lastName,
        String cellphone,
        String email,
        String username
) {
}
