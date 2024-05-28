package com.abello.ecommerce.ecommercereplica.service.mapper;

import com.abello.ecommerce.ecommercereplica.model.Sale;
import com.abello.ecommerce.ecommercereplica.model.dto.response.ProductSoldResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.SaleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SaleOutputMapper {

    SaleResponseDTO toSaleResponseDTO(Sale sale);

    List<ProductSoldResponseDTO> toProductSoldResponseDTO(List<ProductSoldResponseDTO> list);

}
