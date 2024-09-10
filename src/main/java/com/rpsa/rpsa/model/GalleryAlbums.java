package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "galleryalbums")
public class GalleryAlbums {
    @Id
    @Column(nullable = false)
    private String albumid;


    private String albumname;

    @Temporal(TemporalType.DATE)
    @Column(name = "albumdate")
    private Date albumdate;

    private byte[] thumbnail;
}
