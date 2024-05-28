package com.abello.ecommerce.ecommercereplica.service.ports;

import com.abello.ecommerce.ecommercereplica.model.dto.request.HistoryRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.HistoryResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.SaleResponseDTO;

import java.util.List;

public interface IHistoryService {
    HistoryResponseDTO save(HistoryRequestDTO historyRequestDTO);
    boolean remove(Long id);
    HistoryResponseDTO findById(Long id);
    List<HistoryResponseDTO> findAll(Integer offset, Integer pageSize);
    List<SaleResponseDTO> findSales(Long id, Integer offset, Integer pageSize);
    List<SaleResponseDTO> addSale(SaleResponseDTO saleResponseDTO, Long id);
    boolean removeSale(Long id_sale, Long id_history);
}
