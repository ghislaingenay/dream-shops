package com.shoppingcart.dream_shops.service.cart;

import java.math.BigDecimal;

import com.shoppingcart.dream_shops.model.Cart;

public interface ICartService {
  Cart getCartById(Long cartId);

  void clearCart(Long cartId);

  BigDecimal getTotalPrice(Long cartId);

}
