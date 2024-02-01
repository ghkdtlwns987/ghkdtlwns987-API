package com.ghkdtlwns987.apiserver.Catalog.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCatalogDto {
    /* 상품 Id */
    private String productId;

    /* 상품 명 */
    private String productName;

    /* 남은 수량 */
    private Integer qty;

    /* 개당 가격 */
    private Integer unitPrice;

    /* 주문 Id */
    private String orderId;

    /* 유저 Id */
    private String userId;
}