package com.abello.ecommerce.ecommercereplica.model.dto.response;

import java.time.LocalDate;
import java.util.List;

public record SaleResponseDTO(
        Long id,
        String concept,
        List<ProductSoldResponseDTO> products,
        LocalDate createAt,
        Long paymentId
) {
}
