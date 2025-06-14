package com.shoppingcart.dream_shops.model;

import java.util.Collection;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates a unique identifier for each role
  private long id;
  private String name; // Role name (e.g., "USER", "ADMIN", etc.)
  private String description; // Description of the role (optional)

  public Role(String name) {
    this.name = name;
  }

  @ManyToMany(mappedBy = "roles") // Many-to-many relationship with User
  private Collection<User> users = new HashSet<>(); // Collection of users associated with this role

}
