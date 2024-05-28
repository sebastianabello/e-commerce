package com.abello.ecommerce.ecommercereplica.service.mapper;

import com.abello.ecommerce.ecommercereplica.model.CreditCard;
import com.abello.ecommerce.ecommercereplica.model.dto.response.CreditCardResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreditCardOutputMapper {

        CreditCardResponseDTO toCreditCardResponseDTO(CreditCard creditCard);
}
