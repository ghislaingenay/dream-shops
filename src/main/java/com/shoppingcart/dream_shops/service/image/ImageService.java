package com.shoppingcart.dream_shops.service.image;

import org.springframework.web.multipart.MultipartFile;

import com.shoppingcart.dream_shops.model.Image;

public class ImageService implements IImageService {

  @Override
  public Image getImageById(Long id) {
    return null;
  }

  @Override
  public Image saveImage(Long productId, MultipartFile image) {
    return null;
  }

  @Override
  public void updateImage(Long imageId, MultipartFile file) {

  }

  @Override
  public void deleteImageById(Long id) {
  }

}
