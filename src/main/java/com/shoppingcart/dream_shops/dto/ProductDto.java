package com.shoppingcart.dream_shops.dto;

import java.util.List;
import com.shoppingcart.dream_shops.model.Category;

import lombok.Data;

@Data
public class ProductDto {
  private Long productId;
  private String productName;
  private String description;
  private Double price;
  private Category category;
  private List<ImageDto> images;

}
