package com.ghkdtlwns987.apiserver.Order.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderDto {
    /* 상품 Id */
    private String productId;

    /* 상품 명 */
    private String productName;

    /* 남은 수량 */
    private Integer qty;

    /* 개당 가격 */
    private Integer unitPrice;
}
