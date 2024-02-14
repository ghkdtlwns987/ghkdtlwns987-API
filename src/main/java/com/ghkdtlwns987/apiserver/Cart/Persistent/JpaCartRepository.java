package com.ghkdtlwns987.apiserver.Cart.Persistent;

import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;
import com.ghkdtlwns987.apiserver.Cart.Repository.CommandCartRepository;
import org.springframework.data.repository.Repository;

public interface JpaCartRepository extends Repository<Cart, Long>, CommandCartRepository {
}
