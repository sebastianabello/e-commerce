package com.abello.ecommerce.ecommercereplica.service;


import com.abello.ecommerce.ecommercereplica.exceptions.HistoryException;
import com.abello.ecommerce.ecommercereplica.model.History;
import com.abello.ecommerce.ecommercereplica.model.Sale;
import com.abello.ecommerce.ecommercereplica.model.dto.request.HistoryRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.HistoryResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.SaleResponseDTO;
import com.abello.ecommerce.ecommercereplica.repository.HistoryRepository;
import com.abello.ecommerce.ecommercereplica.repository.SaleRepository;
import com.abello.ecommerce.ecommercereplica.service.mapper.HistoryOutputMapper;
import com.abello.ecommerce.ecommercereplica.service.mapper.SaleOutputMapper;
import com.abello.ecommerce.ecommercereplica.service.ports.IHistoryService;
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
public class HistoryServiceImp implements IHistoryService {

    private final HistoryRepository historyRepository;
    private final HistoryOutputMapper historyDTOMapper;
    private final SaleRepository saleRepository;
    private final SaleOutputMapper saleDTOMapper;

    @Override
    public HistoryResponseDTO save(HistoryRequestDTO historyRequestDTO) {
        if(historyRequestDTO!=null){
            History history = new History().builder()
                    .sales(new ArrayList<>())
                    .modificationDate(historyRequestDTO.dateModification())
                    .build();
            return historyDTOMapper.toHistoryResponseDTO(historyRepository.save(history));
        }else{
            throw new HistoryException("The request to save is null");
        }
    }

    @Override
    public boolean remove(Long id) {
     if(historyRepository.existsById(id)){
         historyRepository.deleteById(id);
         return true;
     }else{
         throw new HistoryException("The history fetched to delete doesn't exist");
     }
    }

    @Override
    public HistoryResponseDTO findById(Long id) {
        History history = historyRepository.findById(id).orElse(null);
        if (history!=null){
            return historyDTOMapper.toHistoryResponseDTO(history);
        }else{
            throw new HistoryException("The history fetched by id doesn't exist");
        }
    }

    @Override
    public List<HistoryResponseDTO> findAll(Integer offset, Integer pageSize) {
        Page<History> list = historyRepository.findAll(PageRequest.of(offset,pageSize));
        if(list!=null){
            return list.stream().map(history -> {
                return historyDTOMapper.toHistoryResponseDTO(history);
            }).collect(Collectors.toList());
        }else{
            throw new HistoryException("The list of histories is null");
        }
    }

    @Override
    public List<SaleResponseDTO> findSales(Long id, Integer offset, Integer pageSize) {
        Page<Sale> list = historyRepository.findHistorySales(id,PageRequest.of(offset,pageSize));
        if(list!=null ){
            return list.stream().map(sale -> {
                return saleDTOMapper.toSaleResponseDTO(sale);
            }).collect(Collectors.toList());
        }else{
            throw new HistoryException("The list of history's sales is null");
        }
    }
    //TODO: this method return a pageable with 10 elements in the 0 page
    @Override
    @Transactional
    public List<SaleResponseDTO> addSale(SaleResponseDTO saleResponseDTO, Long id) {
        History history = historyRepository.findById(id).orElse(null);
        Sale sale = saleRepository.findById(saleResponseDTO.id()).orElse(null);
        if(history!=null && sale!=null){
            history.getSales().add(sale);
            history.setModificationDate(LocalDate.now());
            historyRepository.save(history);
            Page<Sale> list = historyRepository.findHistorySales(history.getId(), PageRequest.of(0,10));
            return list.getContent().stream().map(s -> {
                return saleDTOMapper.toSaleResponseDTO(s);
            }).collect(Collectors.toList());
        }else{
            throw new HistoryException("The history fetched or the sale to add doesn't exist");
        }

    }

    @Override
    @Transactional
    public boolean removeSale(Long id_sale, Long id_history) {
        History history = historyRepository.findById(id_history).orElse(null);
        Sale sale = saleRepository.findById(id_sale).orElse(null);
        if(history!=null && sale!=null){
            history.getSales().remove(sale);
            history.setModificationDate(LocalDate.now());
            historyRepository.save(history);
            return true;
        }else{
            throw new HistoryException("The history fetched or the sale fetched doesn't exist");
        }
    }
}
