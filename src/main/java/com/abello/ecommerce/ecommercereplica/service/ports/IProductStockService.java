package com.abello.ecommerce.ecommercereplica.service.ports;

import com.abello.ecommerce.ecommercereplica.model.dto.request.ProductStockRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.ProductStockResponseDTO;

import java.util.List;

public interface IProductStockService {
    ProductStockResponseDTO save(ProductStockRequestDTO productStockRequestDTO);

    boolean validateNameAndBarCode(String name, String barCode);

    boolean remove(Long id);
    ProductStockResponseDTO edit(ProductStockRequestDTO productStockRequestDTO, Long id);
    List<ProductStockResponseDTO> findAll(Integer offset, Integer pageSize);
    ProductStockResponseDTO findProductById(Long id);
    List<ProductStockResponseDTO> findProductStocksByEnableProduct(Integer offset, Integer pageSize);
    ProductStockResponseDTO findProductStockByBarCode(String barCode);
    ProductStockResponseDTO sellProduct(int amountSold, Long id);
}
