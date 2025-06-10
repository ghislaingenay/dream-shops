package com.shoppingcart.dream_shops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.dream_shops.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
