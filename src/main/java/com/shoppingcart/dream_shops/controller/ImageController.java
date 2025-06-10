package com.shoppingcart.dream_shops.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcart.dream_shops.dto.ImageDto;
import com.shoppingcart.dream_shops.response.ApiResponse;
import com.shoppingcart.dream_shops.service.image.IImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
  private final IImageService imageService;

  public ResponseEntity<ApiResponse> saveImages(@RequestParam Long productId, @RequestParam List<MultipartFile> files) {
    try {
      List<ImageDto> imageDtos = imageService.saveImages(productId, files);
      return ResponseEntity.ok(new ApiResponse("Images uploaded successfully", imageDtos));
    } catch (Exception e) {
      return ResponseEntity
          .status(500)
          .body(new ApiResponse("Upload failed" + e.getMessage(), null));
    }

  }
}
