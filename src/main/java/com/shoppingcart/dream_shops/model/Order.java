package com.shoppingcart.dream_shops.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.shoppingcart.dream_shops.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates a unique identifier for each category
  private long id;

  private LocalDate orderDate; // Date when the order was placed
  private BigDecimal totalAmount; // Total amount for the order
  @Enumerated(value = EnumType.STRING) // Store enum as a string in the database
  private OrderStatus orderStatus; // Status of the order (e.g., "Pending", "Shipped", "Delivered")

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) // Maps the relationship to the
                                                                                  // OrderItem entity
  private Set<OrderItem> orderItems; // Set of items in the order

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false) // Foreign key to the User entity
  private User user; // Reference to the user who placed the order

}
