package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Presentations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentationsRepository extends JpaRepository<Presentations, String> {
}
