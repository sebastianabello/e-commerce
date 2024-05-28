package com.abello.ecommerce.ecommercereplica.model.dto.request;

public record ProductStockRequestDTO(
        String name,
        int amount,
        double pricePerUnit,
        String barCode,
        boolean enableProduct
) {
}
