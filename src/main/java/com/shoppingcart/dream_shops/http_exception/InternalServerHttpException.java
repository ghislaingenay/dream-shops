package com.shoppingcart.dream_shops.http_exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerHttpException extends RuntimeException {
  public InternalServerHttpException() {
    super("Internal server error");
  }

  public InternalServerHttpException(String message) {
    super(message);
  }

}
