package com.abello.ecommerce.ecommercereplica.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String concept;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sale_products")
    private List<ProductSold> productsSold;
    @Temporal(TemporalType.DATE)
    private LocalDate createAt;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
