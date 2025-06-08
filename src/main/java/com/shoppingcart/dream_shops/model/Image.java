package com.shoppingcart.dream_shops.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Generates getter methods for all fields (e.g., getId(), getDownloadUrl()).
        // Provided by Lombok.
@Setter // Generates setter methods for all fields (e.g., setId(long id)). Provided by
        // Lombok.
@AllArgsConstructor // Generates a constructor with one parameter for each field in the class (e.g.,
                    // Image(long id, String downloadUrl, ...)).
@NoArgsConstructor // Generates a no-argument constructor (Image()). Provided by Lombok.
@Entity // Marks the class as a JPA entity, meaning it maps to a database table.
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates a unique identifier for each image
                                                      // (e.g., ID 1, 2, 3, ...).
  private long id;
  private String downloadUrl; // URL of the image
  private String fileName;
  private String fileType; // Alternative text for the image
  @Lob // he @Lob annotation tells JPA to map the field to a database BLOB (Binary
       // Large Object) column, which is suitable for storing binary data like images
       // or files.
  private byte[] image; // Using byte[] instead of Blob is often simpler with JPA/Hibernate

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product; // Product associated with the image
}
