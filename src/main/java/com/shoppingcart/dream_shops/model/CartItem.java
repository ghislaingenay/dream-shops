package com.shoppingcart.dream_shops.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  private int quantity; // Quantity of the product in the cart
  private BigDecimal unitPrice;
  private BigDecimal totalPrice; // Total price for this item (quantity * unit price)

  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart; // Reference to the cart this item belongs to*

  public void setTotalPrice() {
    if (product != null && product.getPrice() != null) {
      this.totalPrice = this.unitPrice.multiply(BigDecimal.valueOf(quantity)); // or new BigDecimal(quantity)
    } else {
      this.totalPrice = BigDecimal.ZERO;
    }
  }

}
