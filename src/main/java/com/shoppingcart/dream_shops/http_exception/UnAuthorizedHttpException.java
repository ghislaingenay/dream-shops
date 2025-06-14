package com.shoppingcart.dream_shops.http_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedHttpException extends RuntimeException {
  public UnAuthorizedHttpException() {
    super("Unauthorized access");
  }

  public UnAuthorizedHttpException(String message) {
    super(message);
  }

}
