package com.abello.ecommerce.ecommercereplica.model.dto.request;

public record AddressRequestDTO(
        String street,
        String country,
        String postalCode
) {
}
