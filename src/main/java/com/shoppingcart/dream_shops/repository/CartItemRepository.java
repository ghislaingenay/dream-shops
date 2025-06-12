package com.shoppingcart.dream_shops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.dream_shops.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  void deleteAllByCartId(Long cartId);

  CartItem findByCartIdAndProductId(Long cartId, Long productId);

}
