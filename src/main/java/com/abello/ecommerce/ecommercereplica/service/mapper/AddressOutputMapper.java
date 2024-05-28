package com.abello.ecommerce.ecommercereplica.service.mapper;


import com.abello.ecommerce.ecommercereplica.model.Address;
import com.abello.ecommerce.ecommercereplica.model.dto.response.AddressResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressOutputMapper {

    AddressResponseDTO toAddressResponseDTO(Address address);

}
