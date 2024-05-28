package com.abello.ecommerce.ecommercereplica.controller;

import com.abello.ecommerce.ecommercereplica.model.dto.request.AuthenticationRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.request.CustomerRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.AuthenticationResponseDTO;
import com.abello.ecommerce.ecommercereplica.service.ports.ICustomerService;
import com.abello.ecommerce.ecommercereplica.service.registration.IRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final ICustomerService iCustomerService;
    private final IRegistrationService iRegistrationService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CustomerRequestDTO requestDTO) throws IllegalAccessException {
        AuthenticationResponseDTO authenticationResponseDTO = iRegistrationService.save(requestDTO);
        if (authenticationResponseDTO != null) {
            return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        AuthenticationResponseDTO responseDTO = iCustomerService.authenticate(authenticationRequestDTO);
        if (responseDTO != null) {
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token) {
        return new ResponseEntity<>(iRegistrationService.confirmToken(token), HttpStatus.OK);
    }
}
