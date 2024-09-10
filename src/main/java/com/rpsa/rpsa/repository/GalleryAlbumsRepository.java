package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.GalleryAlbums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryAlbumsRepository extends JpaRepository<GalleryAlbums, String> {

}
