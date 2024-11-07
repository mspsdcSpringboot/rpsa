package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.dto.DoAppellateDTO;
import com.rpsa.rpsa.model.M_Appelate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface M_AppelateRepository extends JpaRepository<M_Appelate, String> {

    @Query("SELECT CAST(MAX(CAST(a.appelateid AS int)) AS int) FROM M_Appelate a")
    Integer findMaxId();


    @Query(value = "SELECT sda.appelateid FROM rpsa.t_service_designated_appelate AS sda WHERE sda.subservicecode = :subservicecode AND sda.officeid = :officeid", nativeQuery = true)
    String findAppellateIdBySubserviceCodeAndOfficeId(@Param("officeid") String officeid, @Param("subservicecode") String subservicecode);

    @Query(value = "SELECT sda.officeid, sda.appelateid FROM rpsa.t_service_designated_appelate AS sda WHERE sda.subservicecode = :subservicecode AND sda.appelateid = :appelateid", nativeQuery = true)
    List<Object[]> checkExistedData(@Param("subservicecode") String subservicecode, @Param("appelateid") String appelateid);

    @Query(value = "SELECT officeid, appelateid FROM rpsa.t_service_designated_appelate WHERE subservicecode = :subservicecode", nativeQuery = true)
    List<Object[]> findAppellateAndDos(@Param("subservicecode") String subservicecode);

    @Query(value = "SELECT officeid FROM rpsa.t_service_designated_appelate WHERE subservicecode = :subservicecode AND appelateid = :appelateid", nativeQuery = true)
    List<String> findDoList(@Param("subservicecode") String subservicecode, @Param("appelateid") String appelateid);

    @Query(value = "SELECT subservicecode FROM rpsa.t_service_designated_appelate", nativeQuery = true)
    List<String> findSubServiceList();



    @Modifying
    @Transactional
    @Query(value = "INSERT INTO rpsa.t_service_designated_appelate (subservicecode, officeid, appelateid) VALUES (:subservicecode, :officeid, :appelateid)", nativeQuery = true)
    void saveappelatemap(String subservicecode, String officeid, String appelateid);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM rpsa.t_service_designated_appelate WHERE subservicecode = :subservicecode AND appelateid = :appelateid", nativeQuery = true)
    void deleteAppellateMap(String subservicecode, String appelateid);

    @Query(value = "SELECT subservicecode FROM rpsa.t_service_designated_appelate WHERE appelateid = :appelateid", nativeQuery = true)
    List<String> findServicesListByAppelateid(String appelateid);


//    @Query(value = "SELECT appelateid FROM rpsa.t_service_designated_appelate " +
//            "WHERE subservicecode = :subservicecode AND officeid = :officeid", nativeQuery = true)
//    String findAppellateIdBySubserviceCodeAndOfficeId(@Param("subservicecode") String subservicecode, @Param("officeid") String officeid);
}
