package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Presentations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentationsRepository extends JpaRepository<Presentations, String> {
    @Query("SELECT CAST(MAX(CAST(p.presentationid AS int)) AS int) FROM Presentations p")
    Integer findMaxId();
}
