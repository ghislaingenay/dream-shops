package com.shoppingcart.dream_shops.service.car_item;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shoppingcart.dream_shops.http_exception.InternalServerHttpException;
import com.shoppingcart.dream_shops.http_exception.NotFoundHttpException;
import com.shoppingcart.dream_shops.model.Cart;
import com.shoppingcart.dream_shops.model.CartItem;
import com.shoppingcart.dream_shops.model.Product;
import com.shoppingcart.dream_shops.repository.CartItemRepository;
import com.shoppingcart.dream_shops.repository.CartRepository;
import com.shoppingcart.dream_shops.service.cart.ICartService;
import com.shoppingcart.dream_shops.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarItemService implements ICarItemService {

  private final CartItemRepository carItemRepository;
  private final CartRepository cartRepository;
  private final IProductService productService;
  private final ICartService cartService;

  @Override
  public void addItemToCart(Long cartId, Long productId, int quantity) {
    // Get the cart
    Cart cart = cartService.getCartById(cartId);
    // Get the porduct
    Product product = productService.getProductById(productId);
    // chek if the product is alrady in the cart
    CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getProduct().getId() == productId)
        .findFirst().orElse(new CartItem());

    if (cartItem.getId() == null) {
      cartItem.setCart(cart);
      cartItem.setProduct(product);
      cartItem.setQuantity(quantity);
      cartItem.setUnitPrice(product.getPrice());

    } else {
      // If the product is already in the cart, increase the quantity
      cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }
    cartItem.setTotalPrice();
    cart.addCartItem(cartItem);
    try {
      carItemRepository.save(cartItem);
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to save cart item: " + e.getMessage());
    }
    try {
      cartRepository.save(cart);
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to save cart: " + e.getMessage());
    }
    // If yes => Increase the quantity
    // else => Add the product to the cart
  }

  @Override

  public void removeItemFromCart(Long cartId, Long productId) {
    Cart cart = cartService.getCartById(cartId);
    CartItem cartItem = getCartItem(cartId, productId);
    cart.removeCartItem(cartItem);
    cartRepository.save(cart);

  }

  @Override
  public void updateItemQuantity(Long cartId, Long productId, int quantity) {
    Cart cart = cartService.getCartById(cartId);
    CartItem cartItem = getCartItem(cartId, productId);
    if (quantity <= 0) {
      removeItemFromCart(cartId, productId);
      return;
    }
    cartItem.setQuantity(quantity);
    cartItem.setUnitPrice(cartItem.getProduct().getPrice());
    cartItem.setTotalPrice();

    // This is a defensive check to ensure the cart's total amount is never null.
    // If, for any reason, getTotalAmount() returns null (maybe the cart is empty or
    // not recalculated), this sets it to BigDecimal.ZERO to avoid
    // NullPointerException in later processing or API responses.
    // It ensures your API always returns a valid number for the cart total.
    BigDecimal totalAmount = cart.getCartItems().stream().map(carItem -> carItem.getTotalPrice())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    cart.setTotalAmount(totalAmount != null ? totalAmount : BigDecimal.ZERO);

    try {
      carItemRepository.save(cartItem);
      cartRepository.save(cart);
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to update cart: " + e.getMessage());
    }
  }

  @Override
  public CartItem getCartItem(Long cartId, Long productId) {
    Cart cart = cartService.getCartById(cartId);
    return cart.getCartItems().stream()
        .filter(item -> item.getProduct().getId() == productId).findFirst()
        .orElseThrow(() -> new NotFoundHttpException("Cart item not found"));

  }
}
