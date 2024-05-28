package com.abello.ecommerce.ecommercereplica.service.ports;

import com.abello.ecommerce.ecommercereplica.model.dto.request.PaymentRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.CreditCardResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.CustomerResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.PaymentResponseDTO;

import java.util.List;

public interface IPaymentService {
    PaymentResponseDTO save (PaymentRequestDTO paymentRequestDTO);
    PaymentResponseDTO edit(PaymentRequestDTO paymentRequestDTO, Long id);
    PaymentResponseDTO changeStatus(String status,Long id);
    List<PaymentResponseDTO> findAll(Integer offset,Integer pageSize);
    PaymentResponseDTO findById(Long id);
    boolean remove(Long id);
    List<PaymentResponseDTO> findPaymentsByStatus(Integer offset, Integer pageSize, String statusPayment);
    CustomerResponseDTO findCustomerPayment(Long id);
    CreditCardResponseDTO findCardPayment(Long id);

}
