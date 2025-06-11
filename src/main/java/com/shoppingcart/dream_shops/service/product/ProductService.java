package com.shoppingcart.dream_shops.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shoppingcart.dream_shops.dto.ImageDto;
import com.shoppingcart.dream_shops.dto.ProductDto;
import com.shoppingcart.dream_shops.http_exception.InternalServerHttpException;
import com.shoppingcart.dream_shops.http_exception.NotFoundHttpException;
import com.shoppingcart.dream_shops.model.Category;
import com.shoppingcart.dream_shops.model.Image;
import com.shoppingcart.dream_shops.model.Product;
import com.shoppingcart.dream_shops.repository.CategoryRepository;
import com.shoppingcart.dream_shops.repository.ImageRepository;
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
  private final ImageRepository imageRepository; // use final and RequiredArgsConstructor to inject

  private final ModelMapper moddelMapper = new ModelMapper();

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
    try {
      return productRepository.save(product);
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to add product: " + e.getMessage());
    }
  }

  /** Hypermethod to help create a product to addProduct */
  private Product createProduct(AddProductRequest request, Category category) {
    return new Product(request.name(), request.brand(), request.price(),
        request.inventory(), request.description(), category);

  }

  @Override
  public Product getProductById(Long productId) {
    try {
      return productRepository.findById(productId).orElseThrow(() -> new NotFoundHttpException(PRODUCT_NOT_FOUND_MSG));
    } catch (NotFoundHttpException e) {
      throw e; // rethrow the exception if it occurs
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to retrieve product: " + e.getMessage());
    }
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
    try {
      productRepository.findById(productId).ifPresentOrElse(productRepository::delete, () -> {
        throw new NotFoundHttpException(PRODUCT_NOT_FOUND_MSG);
      });
    } catch (NotFoundHttpException e) {
      throw e; // rethrow the exception if it occurs
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to delete product: " + e.getMessage());
    }
  }

  @Override
  public List<Product> getAllProducts() {
    try {
      return productRepository.findAll();
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to retrieve products: " + e.getMessage());
    }
  }

  @Override
  public List<Product> getProductsByCategory(Long category) {
    try {
      return productRepository.findByCategoryName(category);
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to retrieve products by category: " + e.getMessage());
    }
  }

  @Override
  public List<Product> getProductsByBrand(String brand) {
    try {
      return productRepository.findByBrand(brand);
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to retrieve products by brand: " + e.getMessage());
    }
  }

  @Override
  public List<Product> getProductsByCategoryAndBrand(Long category, String brand) {
    try {
      return productRepository.findByCategoryNameAndBrand(category, brand);
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to retrieve products by category and brand: " + e.getMessage());
    }
  }

  @Override
  public List<Product> getProductsByName(String name) {
    try {
      return productRepository.getByName(name);
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to retrieve products by name: " + e.getMessage());
    }
  }

  @Override
  public List<Product> getProductsByBrandAndName(String brand, String name) {
    try {
      return productRepository.findByBrandAndName(brand, name);
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to retrieve products by brand and name: " + e.getMessage());
    }
  }

  @Override
  public Long countProductsByBrandAndName(String brand, String name) {
    try {
      return productRepository.countByBrandAndName(brand, name);
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to count products by brand and name: " + e.getMessage());
    }
  }

  @Override
  public List<ProductDto> getConvertedProducts(List<Product> products) {
    return products.stream()
        .map(this::convertToDto)
        .toList();
  }

  @Override
  public ProductDto convertToDto(Product product) {
    ProductDto productDto = moddelMapper.map(product, ProductDto.class);
    List<Image> images = imageRepository.findByProductId(product.getId());
    List<ImageDto> imageDtos = images.stream()
        .map(image -> moddelMapper.map(image, ImageDto.class))
        .toList();
    productDto.setImages(imageDtos);
    return productDto;
  }
}
