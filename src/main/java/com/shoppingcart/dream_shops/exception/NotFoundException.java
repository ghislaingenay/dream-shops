package com.shoppingcart.dream_shops.exception;

// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
  public NotFoundException() {
    super("Resource not found");
  }

  public NotFoundException(String message) {
    super(message);
  }
}