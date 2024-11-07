package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.GalleryAlbums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryAlbumsRepository extends JpaRepository<GalleryAlbums, String> {

    @Query("SELECT CAST(MAX(CAST(n.albumid AS int)) AS int) FROM GalleryAlbums n")
    Integer findMaxId();

}
