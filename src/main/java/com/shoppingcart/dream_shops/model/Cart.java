package com.shoppingcart.dream_shops.model;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private BigDecimal totalAmount = BigDecimal.ZERO; // Total amount of the cart, initialized to zero

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CartItem> cartItems;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public void addCartItem(CartItem cartItem) {
    cartItems.add(cartItem);
    cartItem.setCart(this); // Set the cart reference in the CartItem
    recalculateTotalAmount(); // Recalculate total amount after adding an item
  }

  public void removeCartItem(CartItem cartItem) {
    cartItems.remove(cartItem);
    cartItem.setCart(null); // Clear the cart reference in the CartItem
    recalculateTotalAmount(); // Recalculate total amount after removing an item
  }

  private void recalculateTotalAmount() {
    this.totalAmount = cartItems.stream()
        .map(item -> {
          BigDecimal unitPrice = item.getUnitPrice();
          if (unitPrice == null) {
            return BigDecimal.ZERO; // Default to zero if unit price is null
          }
          return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void clearCart() {
    cartItems.clear(); // Clear all items in the cart
    totalAmount = BigDecimal.ZERO; // Reset total amount to zero
  }

}
