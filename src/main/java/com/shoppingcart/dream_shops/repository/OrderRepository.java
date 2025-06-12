package com.shoppingcart.dream_shops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.dream_shops.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
