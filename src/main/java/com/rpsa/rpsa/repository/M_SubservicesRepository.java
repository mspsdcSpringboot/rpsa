package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Subservices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface M_SubservicesRepository extends JpaRepository<M_Subservices, String> {

    @Query("SELECT CAST(MAX(CAST(s.subservicecode AS int)) AS int) FROM M_Subservices s")
    Integer findMaxId();
    List<M_Subservices> findByServicecodeServicecode(String serviceCode);
}
