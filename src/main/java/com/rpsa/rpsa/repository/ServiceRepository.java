package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<M_Services, String> {

    @Query("SELECT COUNT(ms.servicename) FROM M_Services ms")
    Integer countTotalServiceNames();
}
