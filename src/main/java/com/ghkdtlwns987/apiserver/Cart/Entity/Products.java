package com.ghkdtlwns987.apiserver.Cart.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "catalog_id")
    private List<Items> items = new ArrayList<>();

    @Builder
    public Products(String name, String description, List<Items> items){
        this.name = name;
        this.description = description;
        this.items = items;
    }
}