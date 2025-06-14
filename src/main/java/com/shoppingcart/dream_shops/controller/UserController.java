package com.shoppingcart.dream_shops.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.dream_shops.dto.UserDto;
import com.shoppingcart.dream_shops.model.User;
import com.shoppingcart.dream_shops.request.CreateUserRequest;
import com.shoppingcart.dream_shops.request.UpdateUserRequest;
import com.shoppingcart.dream_shops.response.ApiResponse;
import com.shoppingcart.dream_shops.service.user.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
  private final IUserService userService;

  @GetMapping("/{userId}")
  public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
    User getUser = userService.getUserById(userId);
    UserDto user = userService.convertToDto(getUser);
    return ResponseEntity.ok(new ApiResponse("User retrieved", user));
  }

  @GetMapping("/email")
  public ResponseEntity<ApiResponse> getUserByEmail(@RequestParam String email) {
    User createdUser = userService.getUserByEmail(email);
    UserDto user = userService.convertToDto(createdUser);
    return ResponseEntity.ok(new ApiResponse("User retrieved", user));
  }

  @PostMapping("/")
  public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
    User createdUser = userService.createUser(request);
    UserDto user = userService.convertToDto(createdUser);
    return ResponseEntity.ok(new ApiResponse("User created. Please login", user));
  }

  @PutMapping("/{userId}")
  public ResponseEntity<ApiResponse> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
    User updatedUser = userService.updateUser(userId, request);
    UserDto user = userService.convertToDto(updatedUser);
    return ResponseEntity.ok(new ApiResponse("User updated", user));
  }
}
