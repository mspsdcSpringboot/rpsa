package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "galleryphotos")
public class GalleryPhotos {
    @Id
    @Column(nullable = false)
    private String photoid;
    private String status;

    private byte[] photo;

    @ManyToOne
    @JoinColumn(name = "albumid")
    public GalleryAlbums albumid;
}
