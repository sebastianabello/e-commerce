package com.abello.ecommerce.ecommercereplica.model.dto.request;

public record CustomerRequestDTO(
        String name,
        String lastName,
        String cellphone,
        String email,
        String username,
        String pwd,
        AddressRequestDTO address
) {
}
