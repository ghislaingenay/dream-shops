package com.shoppingcart.dream_shops.http_exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestHttpException extends RuntimeException {
  public BadRequestHttpException() {
    super("Bad request");
  }

  public BadRequestHttpException(String message) {
    super(message);
  }

}
