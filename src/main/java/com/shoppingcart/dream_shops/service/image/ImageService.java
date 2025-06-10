package com.shoppingcart.dream_shops.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcart.dream_shops.dto.ImageDto;
import com.shoppingcart.dream_shops.exception.NotFoundException;
import com.shoppingcart.dream_shops.model.Image;
import com.shoppingcart.dream_shops.model.Product;
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
    return imageRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Image not found"));
  }

  @Override
  public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {
    Product product = productService.getProductById(productId);
    List<ImageDto> savedImageDtos = new ArrayList<>();
    for (MultipartFile file : files) {
      try {
        Image image = new Image();
        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setImage(file.getBytes());
        image.setProduct(product);

        String buildDownloadUrl = "/api/v1/image/download/";
        String downloadUrl = buildDownloadUrl + image.getId();
        image.setDownloadUrl(downloadUrl);

        Image savedImage = imageRepository.save(image);
        savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
        imageRepository.save(savedImage);

        ImageDto imageDto = new ImageDto(savedImage.getId(), savedImage.getFileName(),
            savedImage.getDownloadUrl());
        savedImageDtos.add(imageDto);

      } catch (IOException | DataAccessException e) {
        throw new RuntimeException("Error saving image: " + e.getMessage());
      }
    }
    return savedImageDtos;
  }

  @Override
  public void updateImage(Long imageId, MultipartFile file) {
    Image image = getImageById(imageId);
    try {
      image.setFileName(file.getOriginalFilename());
      image.setFileType(file.getContentType());
      image.setImage(file.getBytes()); // if the file is bloc type => image.setImage(new SerialBlob(file.getBytes()));
      imageRepository.save(image);
    } catch (IOException | DataAccessException e) {
      throw new RuntimeException(e.getMessage());
    }

  }

  @Override
  public void deleteImageById(Long id) {
    imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
      throw new NotFoundException("Image not found");
    });
  }
}
