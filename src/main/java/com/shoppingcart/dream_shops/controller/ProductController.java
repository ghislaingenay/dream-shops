package com.shoppingcart.dream_shops.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.dream_shops.http_exception.BadRequestHttpException;
import com.shoppingcart.dream_shops.model.Product;
import com.shoppingcart.dream_shops.request.AddProductRequest;
import com.shoppingcart.dream_shops.request.ProductUpdateRequest;
import com.shoppingcart.dream_shops.response.ApiResponse;
import com.shoppingcart.dream_shops.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
  private final IProductService productService;

  @GetMapping("/list")
  public ResponseEntity<ApiResponse> getSearchedProducts(@RequestParam(required = false) String brand,
      @RequestParam(required = false) Long category, @RequestParam(required = false) String name) {
    Boolean hasBrand = brand != null && !brand.isEmpty();
    Boolean hasCategory = category != null;
    Boolean hasName = name != null && !name.isEmpty();
    List<Product> products = null;
    if (!hasBrand && !hasName && !hasCategory) {
      products = productService.getAllProducts();
    }
    if (hasCategory && !hasBrand && !hasName) {
      products = productService.getProductsByCategory(category);
    }
    if (hasBrand && !hasCategory && !hasName) {
      products = productService.getProductsByBrand(brand);
    }
    if (hasName && !hasCategory && !hasBrand) {
      products = productService.getProductsByName(name);
      return ResponseEntity.ok(new ApiResponse("Products retrieved", products));
    }
    if (hasCategory && hasBrand && !hasName) {
      products = productService.getProductsByCategoryAndBrand(category, brand);
    }
    if (hasBrand && hasName && !hasCategory) {
      products = productService.getProductsByBrandAndName(brand, name);
    }
    if (products != null && !products.isEmpty()) {
      return ResponseEntity.ok(new ApiResponse("Products retrieved", products));
    }
    throw new BadRequestHttpException(
        "Invalid search parameters. Please provide at least one of: brand, category, or name.");
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
    Product product = productService.getProductById(id);
    return ResponseEntity.ok(new ApiResponse("Product retrieved", product));
  }

  @PostMapping("/create")
  public ResponseEntity<ApiResponse> createProduct(@RequestBody AddProductRequest body) {
    Product savedProduct = productService.addProduct(body);
    return ResponseEntity.ok(new ApiResponse("Product created", savedProduct));
  }

  @PutMapping("/{productId}/update")
  public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId,
      @RequestBody ProductUpdateRequest body) {
    Product updatedProduct = productService.updateProduct(productId, body);
    return ResponseEntity.ok(new ApiResponse("Product updated", updatedProduct));
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
    productService.deleteProductById(productId);
    return ResponseEntity.ok(new ApiResponse("Product deleted", null));
  }

  @GetMapping("/count/by/brand-and-name")
  public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,
      @RequestParam String name) {
    Long count = productService.countProductsByBrandAndName(brand, name);
    return ResponseEntity.ok(new ApiResponse("Product count retrieved", count));
  }

}
