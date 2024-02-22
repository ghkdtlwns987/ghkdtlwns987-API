package com.ghkdtlwns987.apiserver.Cart.Repository;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;
import com.ghkdtlwns987.apiserver.Cart.Persistent.JpaCartRepository;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class CommandCartRepositoryTest {
    private String userId = "7721e64d-c600-4e30-9f22-cdc3262eebde";
    private String name = "장바구니 이름";
    private String description = "상품 설명";
    private String productId1 = "CATALOG-001";
    private String productId2 = "CATALOG-002";
    private String productName1 = "상품 1";
    private String productName2 = "상품 2";
    private Integer qty1 = 3;
    private Integer qty2 = 5;
    private Integer unitPrice1 = 5000;
    private Integer unitPrice2 = 8000;
    CartDto cartDto;

    CartDto.Catalogs catalogs1;
    CartDto.Catalogs catalogs2;
    List<CartDto.Catalogs> catalogsList;
    CartDto.Catalog catalog;

    @Autowired
    JpaCartRepository commandCartRepository;

    @BeforeEach
    void shutdown(){
        catalogs1 = CartDto.Catalogs.builder()
                .productId(productId1)
                .productName(productName1)
                .qty(qty1)
                .unitPrice(unitPrice1)
                .build();

        catalogs2 = CartDto.Catalogs.builder()
                .productId(productId2)
                .productName(productName2)
                .qty(qty2)
                .unitPrice(unitPrice2)
                .build();

        catalogsList = List.of(catalogs1, catalogs2);
        catalog = new CartDto.Catalog(name, description, catalogsList);
        cartDto = new CartDto(userId, List.of(catalog));
    }
    @Test
    void 장바구니_저장_성공_테스트(){
        Cart savingCart = cartDto.toEntity();
        Cart cart = commandCartRepository.save(savingCart);

        assertThat(cart.getId()).isNotNull();
        assertThat(cart.getUserId()).isEqualTo(savingCart.getUserId());
        assertThat(cart.getProducts().get(0).getName()).isEqualTo(savingCart.getProducts().get(0).getName());
        assertThat(cart.getProducts().get(0).getDescription()).isEqualTo(savingCart.getProducts().get(0).getDescription());
        assertThat(cart.getProducts().get(0).getItems().get(0).getProductId()).isEqualTo(savingCart.getProducts().get(0).getItems().get(0).getProductId());
        assertThat(cart.getProducts().get(0).getItems().get(0).getProductName()).isEqualTo(savingCart.getProducts().get(0).getItems().get(0).getProductName());
        assertThat(cart.getProducts().get(0).getItems().get(0).getQty()).isEqualTo(savingCart.getProducts().get(0).getItems().get(0).getQty());
        assertThat(cart.getProducts().get(0).getItems().get(0).getUnitPrice()).isEqualTo(savingCart.getProducts().get(0).getItems().get(0).getUnitPrice());
    }
}
