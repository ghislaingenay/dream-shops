package com.shoppingcart.dream_shops.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

  // Eager fetching: loaded from the database immediately along with the main
  // entity
  @ManyToMany(fetch = FetchType.EAGER, cascade = {
      CascadeType.DETACH,
      CascadeType.PERSIST,
      CascadeType.MERGE,
      CascadeType.REFRESH
  }) // Many-to-many relationship with Role
  @JoinTable(name = "user_roles", // Join table for the many-to-many relationship
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), // Column in the join table that
                                                                                // references User
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")) // Column in the join table that
                                                                                       // references Role
  private Collection<Role> roles = new HashSet<>();
}

// CascadeType.DETACH When
// the parent
// is detached
// from the
// persistence context, the
// child entities
// are also
// detached.

// CascadeType.PERSIST
// When
// the parent

// is persisted (saved for the first time), the child entities are also
// persisted.

// CascadeType.MERGE
// When the parent is merged (updated), the child entities are also merged.

// CascadeType.REFRESH
// When the parent is refreshed from the database, the child entities are also
// refreshed.

// Summary:
// These cascade types let you control which operations on the parent entity
// should also be applied to its related child entities.