package com.shoppingcart.dream_shops.service.order;

import java.util.List;

import com.shoppingcart.dream_shops.dto.OrderDto;

public interface IOrderService {
  OrderDto placeOrder(Long userId);

  OrderDto getOrder(Long orderId);

  List<OrderDto> getOrdersByUserId(Long userId);
}
