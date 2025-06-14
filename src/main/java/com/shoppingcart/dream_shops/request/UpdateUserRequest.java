package com.shoppingcart.dream_shops.request;

import com.shoppingcart.dream_shops.http_exception.BadRequestHttpException;

public record UpdateUserRequest(String firstName, String lastName) {

  public UpdateUserRequest {
    if (firstName == null || firstName.isEmpty()) {
      throw new BadRequestHttpException("First name cannot be empty");
    }
    if (lastName == null || lastName.isEmpty()) {
      throw new BadRequestHttpException("Last name cannot be empty");
    }

  }

}
