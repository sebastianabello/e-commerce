package com.abello.ecommerce.ecommercereplica.model.dto.response;

public record ProductSoldResponseDTO(
        Long id,
        String barCode,
        String name,
        int amount,
        double price
) {
}
