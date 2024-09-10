package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Services;
import com.rpsa.rpsa.model.M_Subservices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface M_SubservicesRepository extends JpaRepository<M_Subservices, String> {


    List<M_Subservices> findByServicecode_Servicecode(String serviceCode);
}
