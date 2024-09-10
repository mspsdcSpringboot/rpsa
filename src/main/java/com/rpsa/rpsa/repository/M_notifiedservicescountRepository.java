package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_notifiedservicescount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface M_notifiedservicescountRepository extends JpaRepository<M_notifiedservicescount, String> {
}
