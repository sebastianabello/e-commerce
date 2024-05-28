package com.abello.ecommerce.ecommercereplica.service;

import com.abello.ecommerce.ecommercereplica.exceptions.SaleException;
import com.abello.ecommerce.ecommercereplica.model.*;
import com.abello.ecommerce.ecommercereplica.model.dto.request.PaymentRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.request.ProductSoldRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.request.SaleRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.PaymentResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.ProductSoldResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.SaleResponseDTO;
import com.abello.ecommerce.ecommercereplica.repository.CreditCardRepository;
import com.abello.ecommerce.ecommercereplica.repository.CustomerRepository;
import com.abello.ecommerce.ecommercereplica.repository.ProductSoldRepository;
import com.abello.ecommerce.ecommercereplica.repository.SaleRepository;
import com.abello.ecommerce.ecommercereplica.service.mapper.PaymentOutputMapper;
import com.abello.ecommerce.ecommercereplica.service.mapper.ProductSoldOutputMapper;
import com.abello.ecommerce.ecommercereplica.service.mapper.SaleOutputMapper;
import com.abello.ecommerce.ecommercereplica.service.ports.ISaleService;
import com.abello.ecommerce.ecommercereplica.utils.StatusPayment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImp implements ISaleService {

    private final SaleRepository saleRepository;
    private final SaleOutputMapper saleDTOMapper;

    private final CustomerRepository customerRepository;

    private final CreditCardRepository creditCardRepository;
    private final ProductSoldRepository productSoldRepository;
    private final ProductSoldOutputMapper productSoldDTOMapper;

    private final PaymentOutputMapper paymentDTOMapper;

    @Override
    public SaleResponseDTO save(SaleRequestDTO saleRequestDTO) {
        if(saleRequestDTO!=null){
            Sale sale;
            if(saleRequestDTO.products()!=null){
                sale=Sale.builder()
                        .concept(saleRequestDTO.concept())
                        .productsSold(productsSold(saleRequestDTO.products()))
                        .createAt(LocalDate.now())
                        .build();
            }else{
                sale=Sale.builder()
                        .concept(saleRequestDTO.concept())
                        .productsSold(new ArrayList<>())
                        .createAt(LocalDate.now())
                        .build();

            }
            Payment payment = existPayment(saleRequestDTO.payment());
            sale.setPayment(payment);
            return saleDTOMapper.toSaleResponseDTO(saleRepository.save(sale));
        }else{
         throw new SaleException("The request to save is null");
        }
    }

    private Payment existPayment(PaymentRequestDTO paymentRequestDTO){
        if(paymentRequestDTO!=null){
            Payment payment = Payment.builder()
                    .statusPayment(knowStatus(paymentRequestDTO.statusPayment()))
                    .createAt(LocalDate.now())
                    .customer(findCustomer(paymentRequestDTO.customer().id()))
                    .card(findCreditCard(paymentRequestDTO.card().id()))
                    .build();
            return payment;
        }else{
            return null;
        }
    }
    private StatusPayment knowStatus(String status){
        switch (status.toLowerCase()){
            case "paid":
                return StatusPayment.PAID;
            case "unpaid":
                return StatusPayment.UNPAID;
            default:
                return null;
        }
    }
    private Customer findCustomer(Long id){
        Customer c = customerRepository.findById(id).orElse(null);
        return c;
    }
    private CreditCard findCreditCard(Long id){
        CreditCard c = creditCardRepository.findById(id).orElse(null);
        return c;
    }
    private List<ProductSold> productsSold(List<ProductSoldRequestDTO> list){
        if(list!=null){
            return list.stream().map(productSoldRequestDTO -> {
                return new ProductSold(
                        null,
                        productSoldRequestDTO.barCode(),
                        productSoldRequestDTO.name(),
                        productSoldRequestDTO.amount(),
                        productSoldRequestDTO.price()
                );
            }).collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }
    //TODO: maybe this method will be refactor
    @Override
    public SaleResponseDTO edit(SaleRequestDTO saleRequestDTO, Long id) {
        Sale sale = saleRepository.findById(id).orElse(null);
        if(sale!=null && saleRequestDTO!=null){
                sale.setConcept(saleRequestDTO.concept());
                sale.setProductsSold(productsSold(saleRequestDTO.products()));
            return saleDTOMapper.toSaleResponseDTO(sale);
        }else{
          throw new SaleException("The sale fetched to update doesn't exist or the request is null");
        }
    }

    @Override
    public SaleResponseDTO findById(Long id) {
        if(saleRepository.existsById(id)){
            return saleDTOMapper.toSaleResponseDTO(saleRepository.findById(id).get());
        }else{
            throw new SaleException("The sale fetched by id doesn't exist");
        }
    }

    @Override
    public List<SaleResponseDTO> findAll(Integer offset, Integer pageSize) {
        Page<Sale> list = saleRepository.findAll(PageRequest.of(offset,pageSize));
        if(list!=null){
          return list.stream().map(sale -> {
              return saleDTOMapper.toSaleResponseDTO(sale);
          }).collect(Collectors.toList());
        }else{
            throw new SaleException("The list of sales is null");
        }
    }

    @Override
    @Transactional
    public SaleResponseDTO addProductSold(ProductSoldRequestDTO productSoldRequesDTO, Long id) {
        Sale sale = saleRepository.findById(id).orElse(null);
        if(sale!=null){
            ProductSold productSold = new ProductSold(
                    null,
                    productSoldRequesDTO.barCode(),
                    productSoldRequesDTO.name(),
                    productSoldRequesDTO.amount(),
                    productSoldRequesDTO.price());
            sale.getProductsSold().add(productSold);
            return saleDTOMapper.toSaleResponseDTO(saleRepository.save(sale));
        }else{
            throw new SaleException("The sale fetched to add it a new product doesn't exist");
        }
    }

    @Override
    @Transactional
    public SaleResponseDTO removeProductSold(Long id_product, Long id_sale) {
        Sale sale = saleRepository.findById(id_sale).orElse(null);
        ProductSold productSold = productSoldRepository.findById(id_product).orElse(null);
        if(sale!=null && productSold!=null){
            sale.getProductsSold().remove(productSold);
            return saleDTOMapper.toSaleResponseDTO(saleRepository.save(sale));
        }else{
            throw new SaleException("The sale fetched or the product to delete doesn't exist");
        }
    }
  //TODO: implement a pageable
    @Override
    public List<ProductSoldResponseDTO> findProductsSold(Long id, Integer offset, Integer pageSize) {
        if(saleRepository.existsById(id)){
            Page<ProductSold> list = saleRepository.findSaleProductsSold(id,PageRequest.of(offset,pageSize));
            if(list!=null){
                return list.stream().map(productSold -> {
                    return productSoldDTOMapper.toProductSoldResponseDTO(productSold);
                }).collect(Collectors.toList());
            }else{
                throw new SaleException("The list of sale's product is null");
            }
        }else{
            throw new SaleException("The sale fetched to find its product doesn't exist");
        }
    }

    @Override
    public PaymentResponseDTO findPayment(Long id) {
        Payment payment = saleRepository.findPaymentSale(id).orElse(null);
        if(payment!=null){
            return paymentDTOMapper.toPaymentResponseDTO(payment);
        }else{
            throw new SaleException("The sale fetch doesn't exist or the sale has not payment yet");
        }

    }
}
