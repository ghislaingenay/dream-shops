package com.shoppingcart.dream_shops.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shoppingcart.dream_shops.model.Product;
import com.shoppingcart.dream_shops.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
  private ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Product addProduct(Product product) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addProduct'");
  }

  @Override
  public Product getProductById(Long productId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProductById'");
  }

  @Override
  public Product updateProduct(Long productId, Product product) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
  }

  @Override
  public void deleteProductById(Long productId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteProductById'");
  }

  @Override
  public List<Product> getAllProducts() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
  }

  @Override
  public List<Product> getProductsByCategory(Long category) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProductsByCategory'");
  }

  @Override
  public List<Product> getProductsByBrand(String brand) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProductsByBrand'");
  }

  @Override
  public List<Product> getProductsByCategoryAndBrand(Long category, String brand) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProductsByCategoryAndBrand'");
  }

  @Override
  public List<Product> getProductsByName(String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProductsByName'");
  }

  @Override
  public List<Product> getProductsByBrandAndName(String brand, String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProductsByBrandAndName'");
  }

  @Override
  public List<Product> getProductsByCategoryAndName(Long category, String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProductsByCategoryAndName'");
  }

  @Override
  public Long countProductsByBrandAndName(String brand, String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'countProductsByBrandAndName'");
  }

}
