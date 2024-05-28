package com.abello.ecommerce.ecommercereplica.service;

import com.abello.ecommerce.ecommercereplica.exceptions.CreditCardException;
import com.abello.ecommerce.ecommercereplica.model.CreditCard;
import com.abello.ecommerce.ecommercereplica.model.dto.request.CreditCardRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.CreditCardResponseDTO;
import com.abello.ecommerce.ecommercereplica.repository.CreditCardRepository;
import com.abello.ecommerce.ecommercereplica.service.mapper.CreditCardOutputMapper;
import com.abello.ecommerce.ecommercereplica.service.ports.ICreditCardService;
import com.abello.ecommerce.ecommercereplica.utils.TypeCard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImp implements ICreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardOutputMapper dtoMapper;


    @Override
    public CreditCardResponseDTO save(CreditCardRequestDTO requestDTO) {
        if(requestDTO!=null){
            CreditCard creditCard = CreditCard.builder()
                    .number(requestDTO.number())
                    .typeCard(typeCard(requestDTO.type()))
                    .build();
            return dtoMapper.toCreditCardResponseDTO(creditCardRepository.save(creditCard));
        }else{
            throw new CreditCardException("The credit card to save is null");
        }
    }

    private TypeCard typeCard(String type){
        return switch (type) {
            case "VISA" -> TypeCard.VISA;
            case "MASTER_CARD" -> TypeCard.MASTER_CARD;
            case "AMERICAN_EXPRESS" -> TypeCard.AMERICAN_EXPRESS;
            default -> null;
        };
    }

    @Override
    public CreditCardResponseDTO edit(CreditCardRequestDTO requestDTO, Long id) {
        CreditCard card = creditCardRepository.findById(id).orElse(null);
        if(card!=null && requestDTO!=null){
            card.setNumber(requestDTO.number());
            card.setTypeCard(typeCard(requestDTO.type()));
            return dtoMapper.toCreditCardResponseDTO(creditCardRepository.save(card));
        }else{
            throw new CreditCardException("The card to update doesn't exist or the request is null");
        }
    }

    @Override
    public CreditCardResponseDTO findById(Long id) {
        CreditCard card = creditCardRepository.findById(id).orElse(null);
        if(card!=null){
            return dtoMapper.toCreditCardResponseDTO(card);
        }else{
            throw new CreditCardException("The card fetched by id doesn't exist");
        }
    }

    @Override
    public CreditCardResponseDTO findCardByNumber(String number) {
        CreditCard card = creditCardRepository.findCreditCardByNumber(number).orElse(null);
        if (card != null){
            return dtoMapper.toCreditCardResponseDTO(card);
        }else{
            throw new CreditCardException("The credit fetched by number doesn't exist");
        }
    }

    @Override
    public List<CreditCardResponseDTO> findAll(Integer offset, Integer pageSize) {
        Page<CreditCard> cards = creditCardRepository.findAll(PageRequest.of(offset,pageSize));
        if(cards!=null && !cards.isEmpty()){
            return cards.getContent().stream().map(creditCard -> {
                return dtoMapper.toCreditCardResponseDTO(creditCard);
            }).collect(Collectors.toList());
        }else{
            throw new CreditCardException("The list of card is null");
        }
    }

    @Override
    public List<CreditCardResponseDTO> findCardsByType(Integer offset, Integer pageSize, String type) {
        Page<CreditCard> cards = creditCardRepository.findCreditCardsByTypeCard(PageRequest.of(offset,pageSize),typeCard(type));
        if(cards!=null){
            return cards.getContent().stream().map(creditCard -> {
                return dtoMapper.toCreditCardResponseDTO(creditCard);
            }).collect(Collectors.toList());
        }else{
            throw new CreditCardException("The list of card is null");
        }
    }

    @Override
    public boolean remove(Long id) {
        if(creditCardRepository.existsById(id)){
            creditCardRepository.deleteById(id);
            return true;
        }else{
            throw new CreditCardException("The card fetched to delete doesn't exist");
        }
    }
}
