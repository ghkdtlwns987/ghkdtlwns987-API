package com.ghkdtlwns987.apiserver.Cart.Dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Cart {
    private String userId;
    private List<Catalog> carts = new ArrayList<>();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Catalog{
        private Long Id;
        private String name;
        private String description;
        private List<Catalogs> catalogs = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Catalogs{
        private Long productId;
        private String productName;
        private Integer qty;
        private Integer unitPrice;
    }


    /*
    public static Cart fromEntity(com.ghkdtlwns987.apiserver.Cart.Entity.Cart cart){
        return new Cart(
                cart.getId(),
                cart.getProductId(),
                cart.getProductName(),
                cart.getQty(),
                cart.getUnitPrice(),
                cart.getMember()
        );
    }

     */
}
