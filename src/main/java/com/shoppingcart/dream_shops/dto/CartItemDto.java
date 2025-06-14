package com.shoppingcart.dream_shops.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemDto {
  private Long itemId;
  private Long productId;
  private ProductDto product;
  private int quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalPrice; // Total price for this cart item (quantity * price)

}
