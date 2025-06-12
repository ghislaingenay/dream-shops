package com.shoppingcart.dream_shops.service.order;

import com.shoppingcart.dream_shops.model.Order;

public interface IOrderService {
  Order placeOrder(Long userId);

  Order getOrder(Long orderId);

}
