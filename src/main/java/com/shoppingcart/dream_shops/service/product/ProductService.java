package com.shoppingcart.dream_shops.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shoppingcart.dream_shops.http_exception.NotFoundHttpException;
import com.shoppingcart.dream_shops.model.Category;
import com.shoppingcart.dream_shops.model.Product;
import com.shoppingcart.dream_shops.repository.CategoryRepository;
import com.shoppingcart.dream_shops.repository.ProductRepository;
import com.shoppingcart.dream_shops.request.AddProductRequest;
import com.shoppingcart.dream_shops.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
  private static final String PRODUCT_NOT_FOUND_MSG = "Product not found";
  private final CategoryRepository categoryRepository; // use final and RequiredArgsConstructor to inject
  // categoryService
  private final ProductRepository productRepository; // use final and RequiredArgsConstructor to inject
                                                     // productRepository
  // This ensures that productRepository is not null and is properly initialized

  @Override
  public Product addProduct(AddProductRequest request) {
    // check if the category exists
    // If yes, set it as the new product category
    // If not, save it as category and as new product category
    Category category = Optional.ofNullable(categoryRepository.findByName(request.category().getName()))
        .orElseGet(() -> {
          Category newCategory = new Category(request.category().getName());
          return categoryRepository.save(newCategory);
        });
    Product product = createProduct(request, category);
    return productRepository.save(product);
  }

  /** Hypermethod to help create a product to addProduct */
  private Product createProduct(AddProductRequest request, Category category) {
    return new Product(request.name(), request.brand(), request.price(),
        request.inventory(), request.description(), category);

  }

  @Override
  public Product getProductById(Long productId) {
    return productRepository.findById(productId).orElseThrow(() -> new NotFoundHttpException(PRODUCT_NOT_FOUND_MSG));
  }

  @Override
  public Product updateProduct(Long productId, ProductUpdateRequest request) {
    return productRepository.findById(productId).map(existingProduct -> updateExistingProduct(existingProduct, request))
        .map(productRepository::save).orElseThrow(() -> new NotFoundHttpException(PRODUCT_NOT_FOUND_MSG));
  }

  private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
    existingProduct.setName(request.name());
    existingProduct.setDescription(request.description());
    existingProduct.setPrice(request.price());
    existingProduct.setInventory(request.inventory());
    existingProduct.setCategory(request.category());
    existingProduct.setBrand(request.brand());
    Category category = categoryRepository.findByName(request.category().getName());
    existingProduct.setCategory(category);
    return existingProduct;
  }

  @Override
  public void deleteProductById(Long productId) {
    productRepository.findById(productId).ifPresentOrElse(productRepository::delete, () -> {
      throw new NotFoundHttpException(PRODUCT_NOT_FOUND_MSG);
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
