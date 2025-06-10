package com.shoppingcart.dream_shops.service.product;

import java.util.List;

import com.shoppingcart.dream_shops.model.Product;

public interface IProductService {
  Product addProduct(Product product);

  Product getProductById(Long productId);

  Product updateProduct(Long productId, Product product);

  void deleteProductById(Long productId);

  List<Product> getAllProducts();

  List<Product> getProductsByCategory(Long category);

  List<Product> getProductsByBrand(String brand);

  List<Product> getProductsByCategoryAndBrand(Long category, String brand);

  List<Product> getProductsByName(String name);

  List<Product> getProductsByBrandAndName(String brand, String name);

  // List<Product> getProductsByCategoryAndName(Long category, String name);

  Long countProductsByBrandAndName(String brand, String name);
}
