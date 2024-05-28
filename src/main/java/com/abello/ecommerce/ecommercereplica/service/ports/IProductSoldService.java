package com.abello.ecommerce.ecommercereplica.service.ports;

import com.abello.ecommerce.ecommercereplica.model.dto.request.ProductSoldRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.ProductSoldResponseDTO;

import java.util.List;

public interface IProductSoldService {
    ProductSoldResponseDTO save(ProductSoldRequestDTO productSoldRequestDTO);
    ProductSoldResponseDTO edit(ProductSoldRequestDTO productSoldRequestDTO, Long id);
    boolean remove(Long id);
    ProductSoldResponseDTO findById(Long id);
    List<ProductSoldResponseDTO> findAll(Integer offset, Integer pageSize);
    List<ProductSoldResponseDTO> findProductsSoldByBarCode(String barcode,Integer offset, Integer pageSize);
}
