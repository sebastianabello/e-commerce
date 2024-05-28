package com.abello.ecommerce.ecommercereplica.service.mapper;

import com.abello.ecommerce.ecommercereplica.model.History;
import com.abello.ecommerce.ecommercereplica.model.Sale;
import com.abello.ecommerce.ecommercereplica.model.dto.response.HistoryResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.SaleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HistoryOutputMapper {

    HistoryResponseDTO toHistoryResponseDTO(History history);
    List<SaleResponseDTO> saleResponseDTOS(List<Sale> sales);
}
