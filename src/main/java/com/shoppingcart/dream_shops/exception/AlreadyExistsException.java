package com.shoppingcart.dream_shops.exception;

public class AlreadyExistsException extends RuntimeException {
  public AlreadyExistsException() {
    super("Resource already exists");
  }

  public AlreadyExistsException(String message) {
    super(message);
  }

}
