package com.shoppingcart.dream_shops.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.dream_shops.model.Cart;
import com.shoppingcart.dream_shops.response.ApiResponse;
import com.shoppingcart.dream_shops.service.car_item.ICarItemService;
import com.shoppingcart.dream_shops.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
  private final ICartService cartService;
  private final ICarItemService cartItemService;

  @GetMapping("/{cartId}")
  public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
    // Implementation to retrieve cart by user ID
    Cart cart = cartService.getCartById(cartId);
    return ResponseEntity.ok(new ApiResponse("Cart retrieved", cart));
  }

  @DeleteMapping("/{cartId}")
  public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
    // Implementation to add product to cart
    cartService.clearCart(cartId);
    return ResponseEntity.ok(new ApiResponse("Cart cleared", true));
  }

  @GetMapping("/{cartId}/cart/amount")
  public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
    // Implementation to retrieve cart by user ID
    BigDecimal totalPrice = cartService.getTotalPrice(cartId);
    return ResponseEntity.ok(new ApiResponse("Total Price", totalPrice));
  }

  @PostMapping("/cart/items/add")
  public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
      @RequestParam Long productId,
      @RequestParam int quantity) {
    if (cartId == null) {
      cartId = cartService.initializeNewCart();
    }
    cartItemService.addItemToCart(cartId, productId, quantity);
    return ResponseEntity.ok(new ApiResponse("Product added to cart", true));
  }

  @DeleteMapping("/{cartId}/cart/items/{productId}/remove")
  public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
    cartItemService.removeItemFromCart(cartId, productId);
    return ResponseEntity.ok(new ApiResponse("Product removed from cart", true));
  }

  @PutMapping("/{cartId}/cart/items/${itemId}/update")
  public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId,
      @RequestParam int quantity) {
    cartItemService.updateItemQuantity(cartId, itemId, quantity);
    return ResponseEntity.ok(new ApiResponse("Product quantity updated", null));
  }

}
