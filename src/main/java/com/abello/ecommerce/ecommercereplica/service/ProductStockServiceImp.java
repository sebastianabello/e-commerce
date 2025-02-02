package com.abello.ecommerce.ecommercereplica.service;


import com.abello.ecommerce.ecommercereplica.exceptions.ProductStockException;
import com.abello.ecommerce.ecommercereplica.model.ProductStock;
import com.abello.ecommerce.ecommercereplica.model.dto.request.ProductStockRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.ProductStockResponseDTO;
import com.abello.ecommerce.ecommercereplica.repository.ProductStockRepository;
import com.abello.ecommerce.ecommercereplica.service.mapper.ProductStockOutputMapper;
import com.abello.ecommerce.ecommercereplica.service.ports.IProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductStockServiceImp implements IProductStockService {

    private final ProductStockRepository productStockRepository;
    private final ProductStockOutputMapper productStockDTOMapper;

    @Override
    public ProductStockResponseDTO save(ProductStockRequestDTO productStockRequestDTO) {
        if (productStockRequestDTO != null) {
            if (validateNameAndBarCode(productStockRequestDTO.name(), productStockRequestDTO.barCode())) {
                throw new ProductStockException("Already exist a product with that name or barcode.");
            } else {
                ProductStock productStock = ProductStock.builder()
                        .name(productStockRequestDTO.name())
                        .amount(productStockRequestDTO.amount())
                        .pricePerUnit(productStockRequestDTO.pricePerUnit())
                        .barCode(productStockRequestDTO.barCode())
                        .enableProduct(productStockRequestDTO.enableProduct())
                        .build();
                return productStockDTOMapper.toProductStockResponseDTO(productStockRepository.save(productStock));
            }
        } else {
            throw new ProductStockException("There not data, the request is null");
        }
    }

    public boolean validateNameAndBarCode(String name, String barCode) {
        return productStockRepository.existsByName(name) || productStockRepository.existsByBarCode(barCode);
    }

    @Override
    public boolean remove(Long id) {
        if (productStockRepository.existsById(id)) {
            productStockRepository.deleteById(id);
            return true;
        } else {
            throw new ProductStockException("The product to delete doesn't exist");
        }
    }

    @Override
    public ProductStockResponseDTO edit(ProductStockRequestDTO productStockRequestDTO, Long id) {
        ProductStock productStock = productStockRepository.findById(id).orElse(null);
        if (productStockRequestDTO != null && productStock != null) {
            productStock.setName(productStockRequestDTO.name());
            productStock.setAmount(productStockRequestDTO.amount());
            productStock.setPricePerUnit(productStockRequestDTO.pricePerUnit());
            productStock.setBarCode(productStockRequestDTO.barCode());
            productStock.setEnableProduct(productStockRequestDTO.enableProduct());
            return productStockDTOMapper.toProductStockResponseDTO(productStockRepository.save(productStock));
        } else {
            throw new ProductStockException("The product to update doesn't exist or the request is null");
        }
    }

    @Override
    public List<ProductStockResponseDTO> findAll(Integer offset, Integer pageSize) {
        Page<ProductStock> list = productStockRepository.findAll(PageRequest.of(offset, pageSize));
        //List<ProductStock> list = productStockRepository.findAll();
        if (list != null) {
            return list.stream().map(productStock -> {
                return productStockDTOMapper.toProductStockResponseDTO(productStock);
            }).collect(Collectors.toList());
        } else {
            throw new ProductStockException("The list of products is null");
        }
    }

    @Override
    public ProductStockResponseDTO findProductById(Long id) {
        ProductStock productStock = productStockRepository.findById(id).orElse(null);
        if (productStock != null) {
            return productStockDTOMapper.toProductStockResponseDTO(productStock);
        } else {
            throw new ProductStockException("The product searched doesn't exist");
        }
    }

    @Override
    public List<ProductStockResponseDTO> findProductStocksByEnableProduct(Integer offset, Integer pageSize) {
        Page<ProductStock> list = productStockRepository.findProductStocksByEnableProduct(PageRequest.of(offset, pageSize));
        if (list != null) {
            return list.stream().map(productStock -> {
                return productStockDTOMapper.toProductStockResponseDTO(productStock);
            }).collect(Collectors.toList());
        } else {
            throw new ProductStockException("The list of products is null");
        }
    }

    @Override
    public ProductStockResponseDTO findProductStockByBarCode(String barCode) {
        ProductStock productStock = productStockRepository.findProductStockByBarCode(barCode).orElse(null);
        if (productStock != null) {
            return productStockDTOMapper.toProductStockResponseDTO(productStock);
        } else {
            throw new ProductStockException("The product searched by barcode doesn't exit");
        }
    }

    @Override
    public ProductStockResponseDTO sellProduct(int amountSold, Long id) {
        ProductStock productStock = productStockRepository.findById(id).orElse(null);
        if (productStock != null) {
            int stockAmount = productStock.getAmount();
            if (stockAmount >= amountSold) {
                productStock.setAmount(stockAmount - amountSold);
                productStock.setEnableProduct(productStock.getAmount() > 0 ? true : false);
                return productStockDTOMapper.toProductStockResponseDTO(productStockRepository.save(productStock));
            } else {
                throw new ProductStockException("The are not the amount requested, sorry");
            }
        } else {
            throw new ProductStockException("The product to sell doesn't exist");
        }
    }
}
