package com.rpsa.rpsa.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "galleryhomepage")
public class GalleryHomePage {
    @Id
    @Column(nullable = false)
    private String photoid;
    private byte[] photo;
}
