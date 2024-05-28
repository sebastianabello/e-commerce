package com.abello.ecommerce.ecommercereplica.controller;


import com.abello.ecommerce.ecommercereplica.exceptions.ProductSoldException;
import com.abello.ecommerce.ecommercereplica.model.dto.request.ProductSoldRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.ProductSoldResponseDTO;
import com.abello.ecommerce.ecommercereplica.service.ports.IProductSoldService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productSold")
public class ProductSoldController {

    private final IProductSoldService iProductSoldService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductSoldRequestDTO productSoldRequestDTO){
        try {
            ProductSoldResponseDTO productSoldResponseDTO = iProductSoldService.save(productSoldRequestDTO);
            return new ResponseEntity<>(productSoldResponseDTO, HttpStatus.CREATED);
        }catch (ProductSoldException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/{id}/edit")
    public ResponseEntity<?> edit(@RequestBody ProductSoldRequestDTO productSoldRequestDTO, @PathVariable("id")Long id){
        try {
            ProductSoldResponseDTO productSoldResponseDTO = iProductSoldService.edit(productSoldRequestDTO,id);
            return new ResponseEntity<>(productSoldResponseDTO, HttpStatus.OK);
        }catch (ProductSoldException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/{id}/remove")
    public ResponseEntity<?> remove(@PathVariable("id")Long id){
        try {
            iProductSoldService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ProductSoldException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id")Long id){
        try {
            ProductSoldResponseDTO productSoldResponseDTO = iProductSoldService.findById(id);
            return new ResponseEntity<>(productSoldResponseDTO,HttpStatus.FOUND);
        }catch (ProductSoldException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset,@PathVariable Integer pageSize){
        try {
            List<ProductSoldResponseDTO> list = iProductSoldService.findAll(offset,pageSize);
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (ProductSoldException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault(){
        try {
            List<ProductSoldResponseDTO> list = iProductSoldService.findAll(0,10);
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (ProductSoldException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/find/barCode/{barcode}/{offset}/{pageSize}")
     public ResponseEntity<?> findProductsSoldByBarCode(@PathVariable("barcode")String barCode,@PathVariable Integer offset,@PathVariable Integer pageSize){
        try {
            List<ProductSoldResponseDTO> list = iProductSoldService.findProductsSoldByBarCode(barCode,offset,pageSize);
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (ProductSoldException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/find/barCode/{barcode}")
    public ResponseEntity<?> findProductsSoldByBarCodeByDefault(@PathVariable("barcode")String barCode){
        try {
            List<ProductSoldResponseDTO> list = iProductSoldService.findProductsSoldByBarCode(barCode,0,10);
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (ProductSoldException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
