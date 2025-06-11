package com.shoppingcart.dream_shops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.dream_shops.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
  // This interface will automatically provide CRUD operations for the Image
  // entity.
  // You can add custom query methods if needed.
  List<Image> findByProductId(Long productId);

}
