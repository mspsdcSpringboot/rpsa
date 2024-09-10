package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface M_ServicesRepository extends JpaRepository<M_Services, String> {

}
