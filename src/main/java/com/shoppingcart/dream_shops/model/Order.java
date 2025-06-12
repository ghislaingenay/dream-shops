package com.shoppingcart.dream_shops.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

}
