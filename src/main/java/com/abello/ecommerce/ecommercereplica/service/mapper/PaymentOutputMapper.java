package com.abello.ecommerce.ecommercereplica.service.mapper;


import com.abello.ecommerce.ecommercereplica.model.Payment;
import com.abello.ecommerce.ecommercereplica.model.dto.response.PaymentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentOutputMapper {

    PaymentResponseDTO toPaymentResponseDTO(Payment payment);

}
