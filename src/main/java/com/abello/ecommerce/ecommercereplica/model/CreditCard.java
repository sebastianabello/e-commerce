package com.abello.ecommerce.ecommercereplica.model;

import com.abello.ecommerce.ecommercereplica.utils.TypeCard;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @Enumerated(EnumType.STRING)
    private TypeCard typeCard;
}
