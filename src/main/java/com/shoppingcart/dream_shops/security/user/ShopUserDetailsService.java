package com.shoppingcart.dream_shops.security.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.shoppingcart.dream_shops.http_exception.UnAuthorizedHttpException;
import com.shoppingcart.dream_shops.model.User;
import com.shoppingcart.dream_shops.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  // This class can be used to load user-specific data from the database or any
  // other source.
  // It can implement methods to fetch user details based on username or other
  // criteria.

  @Override
  public ShopUserDetails loadUserByUsername(String email) {
    User user = Optional.ofNullable(userRepository.findByEmail(email))
        .orElseThrow(() -> new UnAuthorizedHttpException("Invalid email or password"));
    return ShopUserDetails.buildUserDetails(user);
  }

}
