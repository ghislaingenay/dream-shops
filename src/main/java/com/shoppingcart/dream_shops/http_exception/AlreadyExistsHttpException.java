package com.shoppingcart.dream_shops.http_exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsHttpException extends RuntimeException {
  public AlreadyExistsHttpException() {
    super("Resource already exists");
  }

  public AlreadyExistsHttpException(String message) {
    super(message);
  }

}
