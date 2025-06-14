package com.shoppingcart.dream_shops.service.user;

import com.shoppingcart.dream_shops.dto.UserDto;
import com.shoppingcart.dream_shops.model.User;
import com.shoppingcart.dream_shops.request.CreateUserRequest;
import com.shoppingcart.dream_shops.request.UpdateUserRequest;

public interface IUserService {
  User getUserById(Long userId);

  User getUserByEmail(String email);

  User createUser(CreateUserRequest request);

  User updateUser(Long userId, UpdateUserRequest user);

  void deleteUser(Long userId);

  UserDto convertToDto(User user);
}
