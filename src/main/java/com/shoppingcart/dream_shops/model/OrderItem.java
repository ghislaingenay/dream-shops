package com.shoppingcart.dream_shops.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates a unique identifier for each category
  private long id;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order order; // Reference to the order this item belongs to

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product; // Reference to the order this item belongs to

  private int quantity;
  private BigDecimal price;

  public OrderItem(Order order, Product product, BigDecimal price, int quantity) {
    this.price = price;
    this.quantity = quantity;
    this.order = order;
    this.product = product;
  }

}
