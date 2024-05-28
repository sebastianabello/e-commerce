package com.abello.ecommerce.ecommercereplica.service.mapper;

import com.abello.ecommerce.ecommercereplica.model.ProductStock;
import com.abello.ecommerce.ecommercereplica.model.dto.response.ProductStockResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductStockOutputMapper {

    ProductStockResponseDTO toProductStockResponseDTO(ProductStock productStock);


}
