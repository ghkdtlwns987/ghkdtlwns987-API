package com.ghkdtlwns987.apiserver.Cart.Persistent;

import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;
import com.ghkdtlwns987.apiserver.Cart.Entity.QCart;
import com.ghkdtlwns987.apiserver.Cart.Repository.QueryCartRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueryDslQueryCartRepository implements QueryCartRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public Optional<Cart> findCartByUserId(String key) {
        QCart qCart = QCart.cart;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qCart)
                .where(qCart.userId.eq(key))
                .fetchFirst());
    }
}
