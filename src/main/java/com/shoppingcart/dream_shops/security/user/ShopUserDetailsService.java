package com.shoppingcart.dream_shops.security.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public class ShopUserDetailsService implements UserDetailsService {

  // This class can be used to load user-specific data from the database or any
  // other source.
  // It can implement methods to fetch user details based on username or other
  // criteria.

  @Override
  public ShopUserDetails loadUserByUsername(String username) {
    // Logic to load user by username
    return null; // Replace with actual user loading logic
  }

}
