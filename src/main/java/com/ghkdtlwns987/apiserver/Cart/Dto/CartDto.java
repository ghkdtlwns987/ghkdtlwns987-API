package com.ghkdtlwns987.apiserver.Cart.Dto;

import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;
import com.ghkdtlwns987.apiserver.Cart.Entity.Items;
import com.ghkdtlwns987.apiserver.Cart.Entity.Products;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
        private String productId;
        private String productName;
        private Integer qty;
        private Integer unitPrice;
    }

    public Cart toEntity() {
        List<Products> productsList = this.carts.stream()
                .map(catalogDto -> Products.builder()
                        .name(catalogDto.getName())
                        .description(catalogDto.getDescription())
                        .items(buildItemsList(catalogDto.getCatalogs()))
                        .build())
                .collect(Collectors.toList());

        return Cart.builder()
                .userId(this.userId)
                .products(productsList)
                .build();
    }

    private List<Items> buildItemsList(List<Catalogs> catalogDtoItemList) {
        return catalogDtoItemList.stream()
                .map(catalogDtoItem -> Items.builder()
                        .productId(catalogDtoItem.getProductId())
                        .productName(catalogDtoItem.getProductName())
                        .qty(catalogDtoItem.getQty())
                        .unitPrice(catalogDtoItem.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
