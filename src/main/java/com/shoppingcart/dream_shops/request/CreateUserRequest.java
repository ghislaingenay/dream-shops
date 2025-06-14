package com.shoppingcart.dream_shops.request;

import com.shoppingcart.dream_shops.http_exception.BadRequestHttpException;

public record CreateUserRequest(String firstName, String lastName, String email, String password) {

  public CreateUserRequest {
    if (firstName == null || firstName.isEmpty()) {
      throw new BadRequestHttpException("First name cannot be empty");
    }
    if (lastName == null || lastName.isEmpty()) {
      throw new BadRequestHttpException("Last name cannot be empty");
    }
    if (email == null || email.isEmpty()) {
      throw new BadRequestHttpException("Email cannot be empty");
    }
    if (password == null || password.isEmpty()) {
      throw new BadRequestHttpException("Password cannot be empty");
    }
  }

}
