package com.abello.ecommerce.ecommercereplica.model.dto.request;

import com.abello.ecommerce.ecommercereplica.model.dto.response.CreditCardResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.CustomerResponseDTO;

import java.time.LocalDate;

public record PaymentRequestDTO(
        String statusPayment,
        LocalDate createAt,
        CustomerResponseDTO customer,
        CreditCardResponseDTO card
) {
}
