package com.ghkdtlwns987.apiserver.Cart.Repository;

import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;

import java.util.Optional;

public interface QueryCartRepository {

    /**
     * key(userId)로 데이터베이스에 저장된 장바구니 내역을 가져옵니다.
     */
    Optional<Cart> findCartByUserId(String key);
}
