package com.abello.ecommerce.ecommercereplica.service.ports;

import com.abello.ecommerce.ecommercereplica.model.dto.request.ProductSoldRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.request.SaleRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.PaymentResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.ProductSoldResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.SaleResponseDTO;

import java.util.List;

public interface ISaleService {
    SaleResponseDTO save(SaleRequestDTO saleRequestDTO);
    SaleResponseDTO edit(SaleRequestDTO saleRequestDTO,Long id);
    SaleResponseDTO findById(Long id);
    List<SaleResponseDTO> findAll(Integer offset,Integer pageSize);
    SaleResponseDTO addProductSold(ProductSoldRequestDTO productSoldRequestDTO, Long id);
    SaleResponseDTO removeProductSold(Long id_product, Long id_sale);
    List<ProductSoldResponseDTO> findProductsSold(Long id, Integer offset, Integer pageSize);
    PaymentResponseDTO findPayment(Long id);
}
