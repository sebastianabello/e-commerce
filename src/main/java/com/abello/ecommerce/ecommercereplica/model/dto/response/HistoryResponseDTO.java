package com.abello.ecommerce.ecommercereplica.model.dto.response;

import java.time.LocalDate;
import java.util.List;

public record HistoryResponseDTO(
        Long id,
        List<SaleResponseDTO> sales,
        LocalDate dateModification
) {
}
