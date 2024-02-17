package com.ghkdtlwns987.apiserver.Cart.Service;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CommandCartService;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryCartService;
import com.ghkdtlwns987.apiserver.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

public class CommandCartServiceTest extends IntegrationTest {
    final Duration TTL = Duration.ofMillis(5000);

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
    CommandCartService commandCartService;

    @Autowired
    QueryCartService queryCartService;

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
        commandCartService.setValues(userId, cartDto, TTL);
    }

    @AfterEach
    void tearDown(){
        commandCartService.deleteValues(userId);
    }

    @Test
    void 장바구니_조회_테스트(){
        CartDto findValue = queryCartService.getValues(userId);
        assertThat(findValue)
                .isNotNull()
                .extracting(CartDto::getUserId)
                .isEqualTo(userId);
        assertThat(findValue.getCarts().get(0))
                .extracting("name", "description")
                .containsExactlyInAnyOrder(cartDto.getCarts().get(0).getName(), cartDto.getCarts().get(0).getDescription());
        assertThat(findValue.getCarts().get(0).getCatalogs().get(0))
                .extracting("productId", "productName", "qty", "unitPrice")
                .containsExactlyInAnyOrder(
                        cartDto.getCarts().get(0).getCatalogs().get(0).getProductId(),
                        cartDto.getCarts().get(0).getCatalogs().get(0).getProductName(),
                        cartDto.getCarts().get(0).getCatalogs().get(0).getQty(),
                        cartDto.getCarts().get(0).getCatalogs().get(0).getUnitPrice()
                );
    }

    @Test
    void 장바구니_요청_테스트(){
        CartDto cart = commandCartService.setValues(userId, cartDto, TTL);

        assertThat(cart)
                .isNotNull()
                .extracting(CartDto::getUserId)
                .isEqualTo(userId);
        assertThat(cart.getCarts().get(0))
                .extracting("name", "description")
                .containsExactlyInAnyOrder(cartDto.getCarts().get(0).getName(), cartDto.getCarts().get(0).getDescription());
        assertThat(cart.getCarts().get(0).getCatalogs().get(0))
                .extracting("productId", "productName", "qty", "unitPrice")
                .containsExactlyInAnyOrder(
                        cartDto.getCarts().get(0).getCatalogs().get(0).getProductId(),
                        cartDto.getCarts().get(0).getCatalogs().get(0).getProductName(),
                        cartDto.getCarts().get(0).getCatalogs().get(0).getQty(),
                        cartDto.getCarts().get(0).getCatalogs().get(0).getUnitPrice()
                );
    }
    @Test
    void redis_데이터_수정() {
        catalogs1 = CartDto.Catalogs.builder()
                .productId("updateProductId1")
                .productName("updateProductName1")
                .qty(qty1)
                .unitPrice(unitPrice1)
                .build();

        catalogs2 = CartDto.Catalogs.builder()
                .productId("updateProductId2")
                .productName("updateProductName2")
                .qty(qty2)
                .unitPrice(unitPrice2)
                .build();

        catalogsList = List.of(catalogs1, catalogs2);
        catalog = new CartDto.Catalog(name, description, catalogsList);

        CartDto updateCartDto = new CartDto(userId, List.of(catalog));
        CartDto cart = commandCartService.setValues(userId, updateCartDto, TTL);

        assertThat(cart)
                .isNotNull()
                .extracting(CartDto::getUserId)
                .isEqualTo(userId);
        assertThat(cart.getCarts().get(0))
                .extracting("name", "description")
                .containsExactlyInAnyOrder(updateCartDto.getCarts().get(0).getName(), updateCartDto.getCarts().get(0).getDescription());
        assertThat(cart.getCarts().get(0).getCatalogs().get(0))
                .extracting("productId", "productName", "qty", "unitPrice")
                .containsExactlyInAnyOrder(
                        updateCartDto.getCarts().get(0).getCatalogs().get(0).getProductId(),
                        updateCartDto.getCarts().get(0).getCatalogs().get(0).getProductName(),
                        updateCartDto.getCarts().get(0).getCatalogs().get(0).getQty(),
                        updateCartDto.getCarts().get(0).getCatalogs().get(0).getUnitPrice()
                );
    }

    @Test
    void 장바구니_삭제() {
        commandCartService.deleteValues(userId);
        String findValue = String.valueOf(queryCartService.getValues(userId));
        assertThat(findValue).isEqualTo("null");
    }

    @Test
    void 장바구니_만료_테스() {
        String findValue = String.valueOf(queryCartService.getValues(userId));
        await().pollDelay(Duration.ofMillis(6000)).untilAsserted(
                () -> {
                    String expiredValue = String.valueOf(queryCartService.getValues(userId));
                    assertThat(expiredValue).isNotEqualTo(findValue);
                    assertThat(expiredValue).isEqualTo("null");
                }
        );
    }
}
