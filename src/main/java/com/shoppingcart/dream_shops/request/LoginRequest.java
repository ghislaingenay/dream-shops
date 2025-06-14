package com.shoppingcart.dream_shops.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank
  private String email;
  @NotBlank
  private String password;
  // No additional methods or fields are needed for this record class.

}
