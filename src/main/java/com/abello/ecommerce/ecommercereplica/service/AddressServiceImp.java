package com.abello.ecommerce.ecommercereplica.service;


import com.abello.ecommerce.ecommercereplica.exceptions.AddressException;
import com.abello.ecommerce.ecommercereplica.model.Address;
import com.abello.ecommerce.ecommercereplica.model.dto.request.AddressRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.AddressResponseDTO;
import com.abello.ecommerce.ecommercereplica.repository.AddressRepository;
import com.abello.ecommerce.ecommercereplica.service.mapper.AddressOutputMapper;
import com.abello.ecommerce.ecommercereplica.service.ports.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImp implements IAddressService {

    private final AddressRepository addressRepository;
    private final AddressOutputMapper addressDTOMapper;

    @Override
    public AddressResponseDTO save(AddressRequestDTO addressRequestDTO) {
        if (addressRequestDTO != null) {
            Address address = new Address(
                    null,
                    addressRequestDTO.street(),
                    addressRequestDTO.country(),
                    addressRequestDTO.postalCode()
            );
            Address addressSaved = addressRepository.save(address);
            return addressDTOMapper.toAddressResponseDTO(addressSaved);
        } else {
            throw new AddressException("The address to save is null");
        }
    }

    @Override
    public AddressResponseDTO edit(AddressRequestDTO addressRequestDTO, Long id) {
        Address address = addressRepository.findById(id).orElse(null);
        if(address!=null && addressRequestDTO!=null){
            address.setCountry(addressRequestDTO.country());
            address.setStreet(addressRequestDTO.street());
            address.setPostalCode(addressRequestDTO.postalCode());
            return addressDTOMapper.toAddressResponseDTO(addressRepository.save(address));
        }else{
            throw new AddressException("The address to update doesn't exist or the request is null");
        }
    }

    @Override
    public boolean remove(Long id) {
        if(addressRepository.existsById(id)){
            addressRepository.deleteById(id);
            return true;
        }else{
            throw new AddressException("The address to delete doesn't exist");
        }
    }

    @Override
    public AddressResponseDTO findById(Long id) {
        Address address = addressRepository.findById(id).orElse(null);
        if(address!=null){
            return addressDTOMapper.toAddressResponseDTO(address);
        }else{
            throw new AddressException("The address fetched by id doesn't exist");
        }
    }

    @Override
    public List<AddressResponseDTO> findAll(Integer offset, Integer pageSize) {
        Page<Address> list = addressRepository.findAll(PageRequest.of(offset,pageSize));
        if(list!=null && !list.isEmpty()){
            return list.getContent().stream().map(address -> {
                return addressDTOMapper.toAddressResponseDTO(address);
            }).collect(Collectors.toList());
        }else{
            throw new AddressException("The list of addresses fetched is null");
        }
    }

    @Override
    public List<AddressResponseDTO> findAddressesByPostalCode(Integer offset, Integer pageSize, String postalCode) {
        Page<Address> list = addressRepository.findAddressesByPostalCode(PageRequest.of(offset,pageSize),postalCode);
        if(list!=null && !list.isEmpty()){
            return list.getContent()
                    .stream().map(address -> {
                        return addressDTOMapper.toAddressResponseDTO(address);
                    }).collect(Collectors.toList());
        }else{
            throw new AddressException("The list of addresses fetched by postal code is null");
        }
    }

    @Override
    public List<AddressResponseDTO> findAddressesByCountry(Integer offset, Integer pageSize, String country) {
        Page<Address> list = addressRepository.findAddressesByCountry(PageRequest.of(offset,pageSize),country);
        if(list!=null){
            return list
                    .stream().map(address -> {
                        return addressDTOMapper.toAddressResponseDTO(address);
                    }).collect(Collectors.toList());
        }else{
            throw new AddressException("The list of addresses fetched by country code is null");
        }
    }
}
