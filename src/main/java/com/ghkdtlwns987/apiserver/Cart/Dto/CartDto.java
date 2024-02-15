package com.ghkdtlwns987.apiserver.Cart.Dto;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RedisHash("cart")
public class CartDto {
    private String userId;
    private List<Catalog> carts = new ArrayList<>();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Catalog{
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
}
