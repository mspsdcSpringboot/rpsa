package com.rpsa.rpsa.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GalleryPhotoDTO {
    private String photoid;
    private String base64Image;
}
