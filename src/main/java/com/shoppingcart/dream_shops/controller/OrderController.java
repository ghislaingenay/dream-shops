package com.shoppingcart.dream_shops.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.dream_shops.model.Order;
import com.shoppingcart.dream_shops.response.ApiResponse;
import com.shoppingcart.dream_shops.service.order.IOrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
  private final IOrderService orderService;

  @PostMapping("/create")
  /**
   * Creates a new order for the user with the given userId.
   *
   * @param userId the ID of the user placing the order
   * @return a ResponseEntity containing an ApiResponse with the order details
   */
  public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
    Order order = orderService.placeOrder(userId);
    return ResponseEntity.ok(new ApiResponse("Order retrieved ", order));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
    Order order = orderService.getOrder(orderId);
    return ResponseEntity.ok(new ApiResponse("Order retrieved ", order));
  }

  @GetMapping("/user")
  public ResponseEntity<ApiResponse> getOrdersByUserId(Long userId) {
    List<Order> order = orderService.getOrdersByUserId(userId);
    return ResponseEntity.ok(new ApiResponse("Order retrieved ", order));
  }

}
