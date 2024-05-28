package com.abello.ecommerce.ecommercereplica.service;

import com.abello.ecommerce.ecommercereplica.exceptions.PaymentException;
import com.abello.ecommerce.ecommercereplica.model.CreditCard;
import com.abello.ecommerce.ecommercereplica.model.Customer;
import com.abello.ecommerce.ecommercereplica.model.Payment;
import com.abello.ecommerce.ecommercereplica.model.dto.request.PaymentRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.CreditCardResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.CustomerResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.PaymentResponseDTO;
import com.abello.ecommerce.ecommercereplica.repository.CreditCardRepository;
import com.abello.ecommerce.ecommercereplica.repository.CustomerRepository;
import com.abello.ecommerce.ecommercereplica.repository.PaymentRepository;
import com.abello.ecommerce.ecommercereplica.service.mapper.CreditCardOutputMapper;
import com.abello.ecommerce.ecommercereplica.service.mapper.CustomerOutputMapper;
import com.abello.ecommerce.ecommercereplica.service.mapper.PaymentOutputMapper;
import com.abello.ecommerce.ecommercereplica.service.ports.IPaymentService;
import com.abello.ecommerce.ecommercereplica.utils.StatusPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentOutputMapper paymentDTOMapper;

    private final CustomerRepository customerRepository;
    private final CustomerOutputMapper customerDTOMapper;

    private final CreditCardRepository creditCardRepository;
    private final CreditCardOutputMapper creditCardDTOMapper;

    @Override
    public PaymentResponseDTO save(PaymentRequestDTO paymentRequestDTO) {
        if (paymentRequestDTO != null) {
            Payment payment = Payment.builder()
                    .statusPayment(knowStatus(paymentRequestDTO.statusPayment()))
                    .createAt(LocalDate.now())
                    .customer(findCustomer(paymentRequestDTO.customer().id()))
                    .card(findCreditCard(paymentRequestDTO.card().id()))
                    // .sale(findSale(paymentRequestDTO.sale().id()))
                    .build();
            payment = paymentRepository.save(payment);
            return paymentDTOMapper.toPaymentResponseDTO(payment);
        } else {
            throw new PaymentException("The request to save is null");
        }
    }

    private StatusPayment knowStatus(String status) {
        switch (status.toLowerCase()) {
            case "paid":
                return StatusPayment.PAID;
            case "unpaid":
                return StatusPayment.UNPAID;
            default:
                return null;
        }
    }

    private Customer findCustomer(Long id) {
        Customer c = customerRepository.findById(id).orElse(null);
        return c;
    }

    private CreditCard findCreditCard(Long id) {
        CreditCard c = creditCardRepository.findById(id).orElse(null);
        return c;
    }

    @Override
    public PaymentResponseDTO edit(PaymentRequestDTO paymentRequestDTO, Long id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        if (payment != null && paymentRequestDTO != null) {
            payment.setStatusPayment(knowStatus(paymentRequestDTO.statusPayment()));
            payment.setCustomer(findCustomer(paymentRequestDTO.customer().id()));
            payment.setCard(findCreditCard(paymentRequestDTO.card().id()));
            payment.setCreateAt(paymentRequestDTO.createAt());
            payment = paymentRepository.save(payment);
            return paymentDTOMapper.toPaymentResponseDTO(payment);
        } else {
            throw new PaymentException("The payment fetched to update doesn't exist or the request is null");
        }
    }

    @Override
    public PaymentResponseDTO changeStatus(String status, Long id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        if (payment != null) {
            payment.setStatusPayment(knowStatus(status));
            payment = paymentRepository.save(payment);
            return paymentDTOMapper.toPaymentResponseDTO(payment);
        } else {
            throw new PaymentException("The payment fetched to change its status doesn't exist");
        }
    }

    @Override
    public List<PaymentResponseDTO> findAll(Integer offset, Integer pageSize) {
        Page<Payment> list = paymentRepository.findAll(PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(payment -> {
                return paymentDTOMapper.toPaymentResponseDTO(payment);
            }).collect(Collectors.toList());
        } else {
            throw new PaymentException("The list of payments is null");
        }
    }

    @Override
    public PaymentResponseDTO findById(Long id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        if (payment != null) {
            return paymentDTOMapper.toPaymentResponseDTO(payment);
        } else {
            throw new PaymentException("The payment fetched by id doesn't exist");
        }
    }

    @Override
    public boolean remove(Long id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        if (payment != null) {
            paymentRepository.delete(payment);
            return true;
        } else {
            throw new PaymentException("The payment fetched to delete doesn't exist");
        }
    }

    @Override
    public List<PaymentResponseDTO> findPaymentsByStatus(Integer offset, Integer pageSize, String statusPayment) {
        Page<Payment> list = paymentRepository.findPaymentsByStatus(knowStatus(statusPayment), PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(payment -> {
                return paymentDTOMapper.toPaymentResponseDTO(payment);
            }).collect(Collectors.toList());
        } else {
            throw new PaymentException("The list of payments is null");
        }
    }

    @Override
    public CustomerResponseDTO findCustomerPayment(Long id) {
        Customer customer = paymentRepository.findCustomer(id).orElse(null);
        if (customer != null) {
            return customerDTOMapper.toCustomerResponseDTO(customer);
        } else {
            throw new PaymentException("The payment's customer doesn't exist");
        }
    }

    @Override
    public CreditCardResponseDTO findCardPayment(Long id) {
        CreditCard card = paymentRepository.findCard(id).orElse(null);
        if (card != null) {
            return creditCardDTOMapper.toCreditCardResponseDTO(card);
        } else {
            throw new PaymentException("The payment's card doesn't exist");
        }
    }
}
