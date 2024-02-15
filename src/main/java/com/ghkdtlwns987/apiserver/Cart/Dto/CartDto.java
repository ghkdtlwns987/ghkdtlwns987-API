package com.ghkdtlwns987.apiserver.Cart.Dto;

import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartDto {
    private Long Id;
    private String productId;
    private String productName;
    private Integer qty;
    private Integer unitPrice;
    private Member member;


    public static CartDto fromEntity(Cart cart){
        return new CartDto(
                cart.getId(),
                cart.getProductId(),
                cart.getProductName(),
                cart.getQty(),
                cart.getUnitPrice(),
                cart.getMember()
        );
    }
}
