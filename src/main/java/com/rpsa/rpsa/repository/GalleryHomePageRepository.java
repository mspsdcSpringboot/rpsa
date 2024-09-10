package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.GalleryHomePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryHomePageRepository extends JpaRepository<GalleryHomePage, String> {

}
