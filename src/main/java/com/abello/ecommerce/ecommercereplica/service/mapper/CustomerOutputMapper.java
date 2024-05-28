package com.abello.ecommerce.ecommercereplica.service.mapper;

import com.abello.ecommerce.ecommercereplica.model.Customer;
import com.abello.ecommerce.ecommercereplica.model.dto.response.CustomerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerOutputMapper {

    CustomerResponseDTO toCustomerResponseDTO(Customer customer);
}
