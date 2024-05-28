package com.abello.ecommerce.ecommercereplica.service.ports;


import com.abello.ecommerce.ecommercereplica.model.dto.request.AddressRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.AddressResponseDTO;

import java.util.List;

public interface IAddressService {
    AddressResponseDTO save(AddressRequestDTO addressRequestDTO);
    AddressResponseDTO edit(AddressRequestDTO addressRequestDTO, Long id);
    boolean remove(Long id);
    AddressResponseDTO findById(Long id);
    List<AddressResponseDTO> findAll(Integer offset, Integer pageSize);
    List<AddressResponseDTO> findAddressesByPostalCode(Integer offset, Integer pageSize,String postalCode);
    List<AddressResponseDTO> findAddressesByCountry(Integer offset, Integer pageSize,String country);
}
