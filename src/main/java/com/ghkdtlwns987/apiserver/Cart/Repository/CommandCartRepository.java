package com.ghkdtlwns987.apiserver.Cart.Repository;

import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;

public interface CommandCartRepository {
    /**
     * 장바구니에 요소를 저장합니다.
     * @param cart
     * @return CartDto
     */
    Cart save(Cart cart);

    /**
     * 장바구니에 담긴 요소를 삭제합니다.
     * @param cart
     */
    void delete(Cart cart);
}
