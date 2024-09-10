package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Appelate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface M_AppelateRepository extends JpaRepository<M_Appelate, String> {




    @Query(value = "SELECT sda.appelateid FROM rpsa.t_service_designated_appelate AS sda WHERE sda.subservicecode = :subservicecode AND sda.officeid = :officeid", nativeQuery = true)
    String findAppellateIdBySubserviceCodeAndOfficeId(@Param("officeid") String officeid, @Param("subservicecode") String subservicecode);



//    @Query(value = "SELECT appelateid FROM rpsa.t_service_designated_appelate " +
//            "WHERE subservicecode = :subservicecode AND officeid = :officeid", nativeQuery = true)
//    String findAppellateIdBySubserviceCodeAndOfficeId(@Param("subservicecode") String subservicecode, @Param("officeid") String officeid);
}
