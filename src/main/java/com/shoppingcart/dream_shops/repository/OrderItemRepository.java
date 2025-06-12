package com.shoppingcart.dream_shops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.dream_shops.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
