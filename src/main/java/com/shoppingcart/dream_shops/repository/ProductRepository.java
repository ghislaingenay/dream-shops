package com.shoppingcart.dream_shops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.dream_shops.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findByCategoryName(Long category);

  List<Product> findByBrand(String brand);

  List<Product> findByCategoryNameAndBrand(Long category, String brand);

  List<Product> getByName(String name);

  List<Product> findByBrandAndName(String brand, String name);

  Long countByBrandAndName(String brand, String name);

}
