package com.abello.ecommerce.ecommercereplica.model;

import com.abello.ecommerce.ecommercereplica.utils.StatusPayment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private StatusPayment statusPayment;
    private LocalDate createAt;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "credit_card_id")
    private CreditCard card;
}
