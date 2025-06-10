package com.shoppingcart.dream_shops.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shoppingcart.dream_shops.exception.ProductNotFoundException;
import com.shoppingcart.dream_shops.model.Product;
import com.shoppingcart.dream_shops.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
  private final ProductRepository productRepository; // use final and RequiredArgsConstructor to inject
                                                     // productRepository
  // This ensures that productRepository is not null and is properly initialized

  @Override
  public Product addProduct(Product product) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addProduct'");
  }

  @Override
  public Product getProductById(Long productId) {
    return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException());
  }

  @Override
  public Product updateProduct(Long productId, Product product) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
  }

  @Override
  public void deleteProductById(Long productId) {
    productRepository.findById(productId).ifPresentOrElse(productRepository::delete, () -> {
      throw new ProductNotFoundException();
    });
  }

  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public List<Product> getProductsByCategory(Long category) {
    return productRepository.findByCategoryName(category);
  }

  @Override
  public List<Product> getProductsByBrand(String brand) {
    return productRepository.findByBrand(brand);
  }

  @Override
  public List<Product> getProductsByCategoryAndBrand(Long category, String brand) {
    return productRepository.findByCategoryNameAndBrand(category, brand);
  }

  @Override
  public List<Product> getProductsByName(String name) {
    return productRepository.getByName(name);
  }

  @Override
  public List<Product> getProductsByBrandAndName(String brand, String name) {
    return productRepository.findByBrandAndName(brand, name);
  }

  @Override
  public Long countProductsByBrandAndName(String brand, String name) {
    return productRepository.countByBrandAndName(brand, name);
  }

}
