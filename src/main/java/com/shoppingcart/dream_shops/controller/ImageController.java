package com.shoppingcart.dream_shops.controller;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcart.dream_shops.dto.ImageDto;
import com.shoppingcart.dream_shops.http_exception.NotFoundHttpException;
import com.shoppingcart.dream_shops.model.Image;
import com.shoppingcart.dream_shops.response.ApiResponse;
import com.shoppingcart.dream_shops.service.image.IImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
  private final IImageService imageService;

  @PostMapping("/upload")
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

  @GetMapping("/image/download/{id}")
  public ResponseEntity<Resource> downloadImage(@PathVariable("id") Long id) {
    Image imageDto = imageService.getImageById(id);
    ByteArrayResource resource = new ByteArrayResource(imageDto.getImage()); // if blob
                                                                             // image.getImage().getBytes(1, (int)
                                                                             // imageDto.getImage().length()));
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(imageDto.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageDto.getFileName() + "\"")
        .body(resource);

  }

  @PutMapping("/image/{id}/update")
  public ResponseEntity<ApiResponse> updateImage(@PathVariable("id") Long id, @RequestBody MultipartFile file) {
    try {
      Image existingImage = imageService.getImageById(id);
      imageService.updateImage(existingImage.getId(), file);
      return ResponseEntity.ok(new ApiResponse("Image updated successfully", null));
    } catch (NotFoundHttpException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ApiResponse("Image not found: " + e.getMessage(), null));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("Error updating image: " + e.getMessage(), null));
    }
  }

  @DeleteMapping("/image/{id}/delete")
  public ResponseEntity<ApiResponse> deleteImage(@PathVariable("id") Long id) {
    try {
      Image existingImage = imageService.getImageById(id);
      imageService.deleteImageById(existingImage.getId());
      return ResponseEntity.ok(new ApiResponse("Image deleted ", null));
    } catch (NotFoundHttpException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ApiResponse("Image not found: " + e.getMessage(), null));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("Error deleting image: " + e.getMessage(), null));
    }
  }

}
