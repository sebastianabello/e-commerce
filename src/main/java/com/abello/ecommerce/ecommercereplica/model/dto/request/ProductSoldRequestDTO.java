package com.abello.ecommerce.ecommercereplica.model.dto.request;

public record ProductSoldRequestDTO(
        String barCode,
        String name,
        int amount,
        double price
) {
}
