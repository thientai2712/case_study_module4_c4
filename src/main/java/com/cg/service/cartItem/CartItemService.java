package com.cg.service.cartItem;

import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.Product;
import com.cg.service.IGeneralService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface CartItemService extends IGeneralService<CartItem> {
    Optional<CartItem> findByProduct(Product product);

    List<CartItem> findByCart (Cart cart);

    long countCartItemByCart(Cart cart);

    BigDecimal getSumAmount(@Param("cartId") long cartId);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
