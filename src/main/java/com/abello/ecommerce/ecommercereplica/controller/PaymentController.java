package com.abello.ecommerce.ecommercereplica.controller;

import com.abello.ecommerce.ecommercereplica.exceptions.PaymentException;
import com.abello.ecommerce.ecommercereplica.model.dto.request.PaymentRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.CreditCardResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.CustomerResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.PaymentResponseDTO;
import com.abello.ecommerce.ecommercereplica.service.ports.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final IPaymentService iPaymentService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        try {
            PaymentResponseDTO paymentResponseDTO = iPaymentService.save(paymentRequestDTO);
            return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
        } catch (PaymentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody PaymentRequestDTO paymentRequestDTO, @PathVariable Long id) {
        try {
            PaymentResponseDTO paymentResponseDTO = iPaymentService.edit(paymentRequestDTO, id);
            return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
        } catch (PaymentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/change/status/{status}/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable String status, @PathVariable long id) {
        try {
            PaymentResponseDTO paymentResponseDTO = iPaymentService.changeStatus(status, id);
            return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
        } catch (PaymentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            PaymentResponseDTO paymentResponseDTO = iPaymentService.findById(id);
            return new ResponseEntity<>(paymentResponseDTO, HttpStatus.FOUND);
        } catch (PaymentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<PaymentResponseDTO> list = iPaymentService.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (PaymentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<PaymentResponseDTO> list = iPaymentService.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (PaymentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        try {
            iPaymentService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PaymentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/status/{status}/{offset}/{pageSize}")
    public ResponseEntity<?> findPaymentsByStatus(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable String status) {
        try {
            List<PaymentResponseDTO> list = iPaymentService.findPaymentsByStatus(offset, pageSize, status);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (PaymentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/status/{status}")
    public ResponseEntity<?> findPaymentsByStatusDefault(@PathVariable String status) {
        try {
            List<PaymentResponseDTO> list = iPaymentService.findPaymentsByStatus(0, 10, status);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (PaymentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/customer")
    public ResponseEntity<?> findPaymentCustomer(@PathVariable Long id) {
        try {
            CustomerResponseDTO customerResponseDTO = iPaymentService.findCustomerPayment(id);
            return new ResponseEntity<>(customerResponseDTO, HttpStatus.FOUND);
        } catch (PaymentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/card")
    public ResponseEntity<?> findPaymentCard(@PathVariable Long id) {
        try {
            CreditCardResponseDTO creditCardResponseDTO = iPaymentService.findCardPayment(id);
            return new ResponseEntity<>(creditCardResponseDTO, HttpStatus.FOUND);
        } catch (PaymentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
