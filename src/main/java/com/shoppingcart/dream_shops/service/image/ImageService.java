package com.shoppingcart.dream_shops.service.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcart.dream_shops.model.Image;
import com.shoppingcart.dream_shops.repository.ImageRepository;
import com.shoppingcart.dream_shops.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

  private final ImageRepository imageRepository;
  private final IProductService productService;

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
