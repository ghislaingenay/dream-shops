package com.shoppingcart.dream_shops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.dream_shops.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  // Additional query methods can be defined here if needed
  User findByEmail(String email);

}
