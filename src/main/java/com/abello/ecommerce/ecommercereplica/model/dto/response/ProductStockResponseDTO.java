package com.abello.ecommerce.ecommercereplica.model.dto.response;

public record ProductStockResponseDTO(
        Long id,
        String name,
        int amount,
        double pricePerUnit,
        String barCode,
        boolean enableProduct
) {
}
