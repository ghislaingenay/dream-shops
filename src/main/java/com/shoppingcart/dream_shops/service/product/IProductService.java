package com.shoppingcart.dream_shops.service.product;

import java.util.List;

import com.shoppingcart.dream_shops.model.Product;
import com.shoppingcart.dream_shops.request.AddProductRequest;
import com.shoppingcart.dream_shops.request.ProductUpdateRequest;

public interface IProductService {
  Product addProduct(AddProductRequest product);

  Product getProductById(Long productId);

  Product updateProduct(Long productId, ProductUpdateRequest product);

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
