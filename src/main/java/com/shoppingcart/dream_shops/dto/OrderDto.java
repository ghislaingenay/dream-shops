package com.shoppingcart.dream_shops.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.shoppingcart.dream_shops.enums.OrderStatus;
import com.shoppingcart.dream_shops.model.OrderItem;

import lombok.Data;

@Data
public class OrderDto {
  private long id;
  private LocalDate orderDate; // Date when the order was placed
  private BigDecimal totalAmount; // Total amount for the order
  private OrderStatus orderStatus; // Status of the order (e.g., "Pending", "Shipped", "Delivered")
  private Set<OrderItem> orderItems; // Set of items in the order
}
