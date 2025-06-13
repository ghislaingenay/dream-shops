package com.shoppingcart.dream_shops.service.user;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.shoppingcart.dream_shops.http_exception.InternalServerHttpException;
import com.shoppingcart.dream_shops.http_exception.NotFoundHttpException;
import com.shoppingcart.dream_shops.http_exception.UnAuthorizedHttpException;
import com.shoppingcart.dream_shops.model.User;
import com.shoppingcart.dream_shops.repository.UserRepository;
import com.shoppingcart.dream_shops.request.CreateUserRequest;
import com.shoppingcart.dream_shops.request.UpdateUserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
  private final UserRepository userRepository;

  @Override
  public User getUserById(Long userId) {
    try {
      return userRepository.findById(userId).orElseThrow(() -> new NotFoundHttpException("User not found"));
    } catch (DataAccessException e) {
      throw new InternalServerHttpException(
          "Failed to retrieve user with ID: " + userId + ". Error: " + e.getMessage());

    } catch (Exception e) {
      throw new InternalServerHttpException("An unexpected error occurred while retrieving user with ID: " + userId);
    }
  }

  @Override
  public User getUserByEmail(String email) {
    try {
      return userRepository.findByEmail(email);
    } catch (DataAccessException e) {
      throw new InternalServerHttpException(
          "Failed to retrieve user with ID: " + e.getMessage());

    }
  }

  @Override
  public User createUser(CreateUserRequest request) {
    User newUser = new User();
    Optional.ofNullable(userRepository.findByEmail(request.email()))
        .ifPresentOrElse(u -> {
          throw new UnAuthorizedHttpException("User already exists");
        }, () -> {
          newUser.setFirstName(request.firstName());
          newUser.setLastName(request.lastName());
          newUser.setEmail(request.email());
          newUser.setPassword(request.password());
          // User does not exist, proceed to create a new user
        });
    try {
      return userRepository.save(newUser);
    } catch (DataAccessException e) {
      throw new InternalServerHttpException("Failed to create user: " + e.getMessage());
    } catch (Exception e) {
      throw new InternalServerHttpException("An unexpected error occurred while creating user: " + e.getMessage());
    }

  }

  @Override
  public User updateUser(Long userId, UpdateUserRequest request) {
    return userRepository.findById(userId).map(existingUser -> {
      existingUser.setFirstName(request.firstName());
      existingUser.setLastName(request.lastName());
      userRepository.save(existingUser);
      return existingUser;
    }).orElseThrow(() -> new NotFoundHttpException("User not found"));
  }

  @Override
  public void deleteUser(Long userId) {
    userRepository.findById(userId).ifPresentOrElse(
        user -> {
          try {
            userRepository.delete(user);
          } catch (DataAccessException e) {
            throw new InternalServerHttpException(
                "Failed to delete user : " + e.getMessage());
          }
        },
        () -> {
          throw new NotFoundHttpException("User not found");
        });
  }

}
