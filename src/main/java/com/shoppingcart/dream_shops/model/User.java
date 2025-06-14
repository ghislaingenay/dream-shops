package com.shoppingcart.dream_shops.model;

import java.util.List;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY) // Automatically generates a unique identifier
  private long id;

  private String firstName;
  private String lastName;
  @NaturalId
  private String email;
  private String password;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) // Assuming a one-to-one relationship with Cart
  private Cart cart; // Assuming Cart is another entity that represents the user's shopping cart

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // Maps the relationship to the Order
                                                                                 // entity
  private List<Order> order;
}
