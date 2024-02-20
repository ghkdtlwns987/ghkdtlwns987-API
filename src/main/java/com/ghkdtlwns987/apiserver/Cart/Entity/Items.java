package com.ghkdtlwns987.apiserver.Cart.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String productId;
    private String productName;
    private Integer qty;
    private Integer unitPrice;

    @Builder
    public Items(String productId, String productName, Integer qty, Integer unitPrice){
        this.productId = productId;
        this.productName = productName;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
}