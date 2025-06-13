package com.shoppingcart.dream_shops.dto;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;

@Data
public class CartDto {
  private Long cartId;
  private Set<CartItemDto> cartItems; // Set of items in the cart
  private BigDecimal totalAmount;
}
