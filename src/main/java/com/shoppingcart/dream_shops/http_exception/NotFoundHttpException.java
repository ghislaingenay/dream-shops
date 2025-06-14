package com.shoppingcart.dream_shops.http_exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundHttpException extends RuntimeException {
  public NotFoundHttpException() {
    super("Resource not found");
  }

  public NotFoundHttpException(String message) {
    super(message);
  }

}
