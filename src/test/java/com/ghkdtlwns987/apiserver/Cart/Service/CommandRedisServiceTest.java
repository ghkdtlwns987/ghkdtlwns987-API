package com.ghkdtlwns987.apiserver.Cart.Service;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;
import com.ghkdtlwns987.apiserver.Cart.Repository.CommandRedisRepository;
import com.ghkdtlwns987.apiserver.Cart.Service.Impl.CommandRedisServiceImpl;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryRedisService;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EntityListeners(AuditingEntityListener.class)
@ExtendWith(MockitoExtension.class)
public class CommandRedisServiceTest {
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
    Cart cart;

    CartDto.Catalogs catalogs1;
    CartDto.Catalogs catalogs2;
    List<CartDto.Catalogs> catalogsList;
    CartDto.Catalog catalog;
    CommandRedisRepository commandRedisRepository;
    CommandRedisServiceImpl commandRedisService;

    @BeforeEach
    void setUp(){
        commandRedisRepository = Mockito.mock(CommandRedisRepository.class);
        commandRedisService = new CommandRedisServiceImpl(commandRedisRepository);
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
        cart = cartDto.toEntity();
    }

    @Test
    void Redis에_장바구니_등록_성공(){
        doReturn(cartDto).when(commandRedisRepository).setValues(any(String.class), any(CartDto.class), any(Duration.class));

        // when
        CartDto result = commandRedisService.saveCartForCache(userId, cartDto);

        assertThat(result.getUserId()).isEqualTo(cartDto.getUserId());
        assertThat(result.getCarts().get(0).getName()).isEqualTo(cartDto.getCarts().get(0).getName());
        assertThat(result.getCarts().get(0).getDescription()).isEqualTo(cartDto.getCarts().get(0).getDescription());
        assertThat(result.getCarts().get(0).getCatalogs().get(0).getProductId()).isEqualTo(cartDto.getCarts().get(0).getCatalogs().get(0).getProductId());
        assertThat(result.getCarts().get(0).getCatalogs().get(0).getProductName()).isEqualTo(cartDto.getCarts().get(0).getCatalogs().get(0).getProductName());
        assertThat(result.getCarts().get(0).getCatalogs().get(0).getQty()).isEqualTo(cartDto.getCarts().get(0).getCatalogs().get(0).getQty());
        assertThat(result.getCarts().get(0).getCatalogs().get(0).getUnitPrice()).isEqualTo(cartDto.getCarts().get(0).getCatalogs().get(0).getUnitPrice());
    }

    @Test
    void Redis에_담긴_장바구니_삭제(){
        doNothing().when(commandRedisRepository).deleteValues(any(String.class));
        commandRedisService.deleteCartForCache(userId);
        verify(commandRedisRepository, times(1)).deleteValues(any(String.class));
    }
}
