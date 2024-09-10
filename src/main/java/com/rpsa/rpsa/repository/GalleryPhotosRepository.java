package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.GalleryAlbums;
import com.rpsa.rpsa.model.GalleryPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody

public interface GalleryPhotosRepository extends JpaRepository<GalleryPhotos, String> {

    List<GalleryPhotos> findByAlbumid(GalleryAlbums album);
}
