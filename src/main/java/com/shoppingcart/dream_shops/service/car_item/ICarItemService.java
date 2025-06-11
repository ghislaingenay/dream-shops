package com.shoppingcart.dream_shops.service.car_item;

public interface ICarItemService {
  void addItemToCart(Long cartId, Long productId, int quantity);

  void removeItemFromCart(Long cartId, Long productId);

  void updateItemQuantity(Long cartId, Long productId, int quantity);

}
