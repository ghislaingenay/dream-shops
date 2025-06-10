package com.shoppingcart.dream_shops.service.image;

import org.springframework.web.multipart.MultipartFile;

import com.shoppingcart.dream_shops.model.Image;

public interface IImageService {
  Image getImageById(Long id);

  void deleteImageById(Long id);

  Image saveImage(Long productId, MultipartFile file);

  void updateImage(Long imageId, MultipartFile file);

}
