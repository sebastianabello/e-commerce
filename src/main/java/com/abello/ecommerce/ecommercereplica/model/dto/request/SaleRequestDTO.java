package com.abello.ecommerce.ecommercereplica.model.dto.request;


import java.time.LocalDate;
import java.util.List;

public record SaleRequestDTO(
        String concept,
        List<ProductSoldRequestDTO> products,
        LocalDate createAt,
        PaymentRequestDTO payment
) {
}
