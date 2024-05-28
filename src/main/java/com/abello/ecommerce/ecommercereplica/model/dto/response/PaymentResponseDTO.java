package com.abello.ecommerce.ecommercereplica.model.dto.response;

import java.util.Date;

public record PaymentResponseDTO(
        Long id,
        String statusPayment,
        Date createAt,
        Long customerId,
        Long creditCardId
) {
}
