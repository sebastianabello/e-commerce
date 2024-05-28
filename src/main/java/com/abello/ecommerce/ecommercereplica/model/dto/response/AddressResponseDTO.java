package com.abello.ecommerce.ecommercereplica.model.dto.response;

public record AddressResponseDTO(
        Long id,
        String street,
        String country,
        String postalCode
) {
}
