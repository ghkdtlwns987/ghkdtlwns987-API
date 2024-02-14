package com.ghkdtlwns987.apiserver.Cart.Repository;

import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;

public interface CommandCartRepository {
    /**
     * 장바구니에 요소를 저장합니다.
     * @param cart
     * @return Cart
     */
    Cart save(Cart cart);


}
