package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Audit_Trail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Audit_TrailRepository extends JpaRepository<Audit_Trail, String> {
    @Query("SELECT CAST(MAX(CAST(a.auditid AS int)) AS int) FROM Audit_Trail a")
    Integer findMaxId();
}
