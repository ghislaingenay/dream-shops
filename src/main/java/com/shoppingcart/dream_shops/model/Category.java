package com.shoppingcart.dream_shops.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates a unique identifier for each category
  private long id;
  private String name;

  /**
   * Reqson to use @JSonIgnore
   * To prevent infinite recursion in bidirectional relationships (like Category ↔
   * Product).
   * To avoid sending unnecessary or sensitive data in API responses.
   * To reduce the size of the JSON response.
   */

  @JsonIgnore // not to include the annotated field in JSON serialization or deserialization
  // This means when you return a Category object as JSON (e.g., in a REST API
  // response), the products list will not be included in the JSON output
  @OneToMany(mappedBy = "category")
  private List<Product> products;

  public Category(String name) {
    this.name = name;
  }

}
