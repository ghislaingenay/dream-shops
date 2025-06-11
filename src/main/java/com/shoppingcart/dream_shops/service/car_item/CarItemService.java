package com.shoppingcart.dream_shops.service.car_item;

import org.springframework.stereotype.Service;

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
    carItemRepository.save(cartItem);
    cartRepository.save(cart);
    // If yes => Increase the quantity
    // else => Add the product to the cart
  }

  @Override

  public void removeItemFromCart(Long cartId, Long productId) {
  }

  @Override
  public void updateItemQuantity(Long cartId, Long productId, int quantity) {
  }

}
