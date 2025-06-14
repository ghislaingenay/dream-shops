package com.shoppingcart.dream_shops.dto;

import java.util.List;

import com.shoppingcart.dream_shops.model.Cart;

import lombok.Data;

@Data
public class UserDto {
  private long id;

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private List<OrderDto> orders;
  private CartDto cart;
}
