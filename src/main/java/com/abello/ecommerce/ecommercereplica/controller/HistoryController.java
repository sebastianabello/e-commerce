package com.abello.ecommerce.ecommercereplica.controller;

import com.abello.ecommerce.ecommercereplica.exceptions.HistoryException;
import com.abello.ecommerce.ecommercereplica.model.dto.request.HistoryRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.HistoryResponseDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.SaleResponseDTO;
import com.abello.ecommerce.ecommercereplica.service.ports.IHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
public class HistoryController {

    private final IHistoryService iHistoryService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody HistoryRequestDTO historyRequestDTO){
     try {
         HistoryResponseDTO historyResponseDTO = iHistoryService.save(historyRequestDTO);
         return new ResponseEntity<>(historyResponseDTO, HttpStatus.CREATED);
     }catch (HistoryException ex){
         return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
     }

    }
    @DeleteMapping("/{id}/remove")
    public ResponseEntity<?> remove(@PathVariable("id")Long id){
     try {
         iHistoryService.remove(id);
         return new ResponseEntity<>(HttpStatus.OK);
     }catch (HistoryException ex){
         return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
     }

    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id")Long id){
        try {
            HistoryResponseDTO historyResponseDTO = iHistoryService.findById(id);
            return new ResponseEntity<>(historyResponseDTO,HttpStatus.FOUND);
        }catch (HistoryException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset,@PathVariable Integer pageSize){
        try {
            List<HistoryResponseDTO> list = iHistoryService.findAll(offset, pageSize);
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (HistoryException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault(){
        try {
            List<HistoryResponseDTO> list = iHistoryService.findAll(0, 10);
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (HistoryException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}/find/sales/{offset}/{pageSize}")
    public ResponseEntity<?> findSales(@PathVariable("id")Long id, @PathVariable Integer offset,@PathVariable Integer pageSize){
        try {
            List<SaleResponseDTO> list = iHistoryService.findSales(id,offset,pageSize);
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (HistoryException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}/find/sales")
    public ResponseEntity<?> findSalesByDefault(@PathVariable("id")Long id){
        try {
            List<SaleResponseDTO> list = iHistoryService.findSales(id,0,10);
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (HistoryException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{id}/add/sale")
    public ResponseEntity<?> addSale(@RequestBody SaleResponseDTO saleResponseDTO, @PathVariable("id")Long id){
        try {
            List<SaleResponseDTO> list = iHistoryService.addSale(saleResponseDTO,id);
            return new ResponseEntity<>(list,HttpStatus.OK);
        }catch (HistoryException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/{id_history}/remove/sale/{id_sale}")
    public ResponseEntity<?> removeSale(@PathVariable("id_sale")Long id_sale,@PathVariable("id_history")Long id_history){
        try {
            iHistoryService.removeSale(id_sale,id_history);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (HistoryException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
