package com.shoppingcart.dream_shops.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shoppingcart.dream_shops.dto.ImageDto;
import com.shoppingcart.dream_shops.model.Image;

public interface IImageService {
  Image getImageById(Long id);

  void deleteImageById(Long id);

  List<ImageDto> saveImages(Long productId, List<MultipartFile> files);

  void updateImage(Long imageId, MultipartFile file);

}
