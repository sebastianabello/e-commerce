package com.abello.ecommerce.ecommercereplica.model;

import com.abello.ecommerce.ecommercereplica.utils.TokenType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private boolean revoked;

    private boolean expired;
}
