package com.shoppingcart.dream_shops.exception;

public class CategoryNotFoundException extends RuntimeException {
  public CategoryNotFoundException(String message) {
    super(message);
  }

  public CategoryNotFoundException() {
    super("Category not found");
  }

  public CategoryNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

}
