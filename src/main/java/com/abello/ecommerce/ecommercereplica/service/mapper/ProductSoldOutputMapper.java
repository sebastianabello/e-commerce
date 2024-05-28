package com.abello.ecommerce.ecommercereplica.service.mapper;


import com.abello.ecommerce.ecommercereplica.model.ProductSold;
import com.abello.ecommerce.ecommercereplica.model.dto.response.ProductSoldResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductSoldOutputMapper {

    ProductSoldResponseDTO toProductSoldResponseDTO(ProductSold productSold);

}
