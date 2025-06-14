package com.shoppingcart.dream_shops.service.cart;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcart.dream_shops.http_exception.InternalServerHttpException;
import com.shoppingcart.dream_shops.http_exception.NotFoundHttpException;
import com.shoppingcart.dream_shops.model.Cart;
import com.shoppingcart.dream_shops.model.User;
import com.shoppingcart.dream_shops.repository.CartItemRepository;
import com.shoppingcart.dream_shops.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final AtomicLong cartIdGenerator = new AtomicLong(0);

  @Override
  public Cart getCartById(Long cartId) {
    try {
      Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundHttpException("Cart not found"));
      BigDecimal totalAmount = cart.getTotalAmount();
      cart.setTotalAmount(totalAmount != null ? totalAmount : BigDecimal.ZERO);
      return cart;
    } catch (NotFoundHttpException e) {
      throw e; // Rethrow the NotFoundHttpException if it occurs
    } catch (Exception e) {
      throw new InternalServerHttpException(e.getMessage());
      // Handle exception, log error, etc.
    }
  }

  @Transactional // if one fail, everything fails
  // and rollback
  @Override
  public void clearCart(Long cartId) {
    Cart cart = getCartById(cartId);
    cartItemRepository.deleteAllByCartId(cartId);
    cart.clearCart();
    cartRepository.deleteById(cartId);
    // Implementation to clear the cart
    // Placeholder implementation
  }

  @Override
  public BigDecimal getTotalPrice(Long cartId) {
    Cart cart = getCartById(cartId);
    return cart.getTotalAmount();
  }

  @Override
  public Cart initializeNewCart(User user) {
    return Optional.ofNullable(getCartByUserId(user.getId()))
        .orElseGet(() -> {
          Cart newCart = new Cart();
          newCart.setUser(user);
          return cartRepository.save(newCart);
        });
  }

  @Override
  public Cart getCartByUserId(Long userId) {
    try {
      return Optional.ofNullable(cartRepository.findByUserId(userId))
          .orElseThrow(() -> new NotFoundHttpException("Cart not found for user"));
    } catch (NotFoundHttpException e) {
      throw e; // Rethrow the NotFoundHttpException if it occurs
    } catch (Exception e) {
      throw new InternalServerHttpException(e.getMessage());
      // Handle exception, log error, etc.
    }
  }

}
