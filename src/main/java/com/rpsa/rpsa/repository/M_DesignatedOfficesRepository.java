package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Designatedoffices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface M_DesignatedOfficesRepository extends JpaRepository<M_Designatedoffices, String> {

    @Query("SELECT CAST(MAX(CAST(d.officeid AS int)) AS int) FROM M_Designatedoffices d")
    Integer findMaxId();

//    @Query(value = "SELECT \n" +
//            "    subservices.subservicename,\n" +
//            "    designatedoffices.officeid," +
//            "    designatedoffices.officename\n" +
//            "FROM \n" +
//            "    rpsa.t_service_designated_appelate AS sda\n" +
//            "JOIN \n" +
//            "    rpsa.m_subservices AS subservices ON sda.subservicecode = subservices.subservicecode\n" +
//            "JOIN \n" +
//            "    rpsa.m_designatedoffices AS designatedoffices ON sda.officeid = designatedoffices.officeid\n" +
//            "WHERE \n" +
//            "    subservices.servicecode = :subservicecode", nativeQuery = true)
//    List<Map<String, Object>> getAll(String subservicecode);



    @Query(value = "SELECT DISTINCT(d.officeid), d.officename " +
            "FROM rpsa.m_designatedoffices d, rpsa.t_service_designated_appelate sda " +
            "WHERE sda.subservicecode = :subservicecode " +
            "AND d.officeid = sda.officeid " +
            "ORDER BY d.officename", nativeQuery = true)
    List<Map<String, Object>> getAll(@Param("subservicecode") String subservicecode);

}
