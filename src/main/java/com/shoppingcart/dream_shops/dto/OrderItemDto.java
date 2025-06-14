package com.shoppingcart.dream_shops.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
// Refers to a product in an order
// Contains details about the product, quantity, and price
public class OrderItemDto {
  private Long productId;
  private String productName;
  private int quantity;
  private BigDecimal price;
}
