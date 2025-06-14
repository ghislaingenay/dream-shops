package com.shoppingcart.dream_shops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.dream_shops.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
  // Additional query methods can be defined here if needed
  Cart findByUserId(Long userId);

}
