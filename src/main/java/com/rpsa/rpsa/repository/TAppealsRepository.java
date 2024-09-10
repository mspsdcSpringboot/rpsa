package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.T_Appeals;
import com.rpsa.rpsa.model.T_userlogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TAppealsRepository extends JpaRepository<T_Appeals, String> {
    @Query("SELECT CAST(MAX(CAST(a.appealcode AS int)) AS int) FROM T_Appeals a")
    Integer findMaxId();

    T_Appeals findByRefno(String appealcode);

    List<T_Appeals> findAllByUsercode(T_userlogin user);


    @Query("FROM T_Appeals ta WHERE ta.appelateid.appelateid = :appelateid AND " +
            "ta.statusid.statusid = '0' AND ta.appeallevel = '1'")
    List<T_Appeals> getAppelateAppeals(@Param("appelateid") String appelateid);

    @Query("FROM T_Appeals ta WHERE ta.appelateid.appelateid = :appelateid AND " +
            "(ta.appelateactioncode IS NOT NULL AND ta.appelateactioncode.actioncode <> '4' " +
            "AND ta.appelateactioncode.actioncode <> '2' AND ta.appelateactioncode.actioncode <> '3' " +
            "AND ta.appelateactioncode.actioncode <> '9' AND ta.appelateactioncode.actioncode <> '10') " +
            "AND ta.appeallevel = '1'")
    List<T_Appeals> getProcessedAppelateAppeals(@Param("appelateid") String appelateid);

    @Query("FROM T_Appeals ta WHERE ta.appelateid.appelateid = :appelateid AND " +
            "ta.appelateactioncode.actioncode = '4' AND ta.appeallevel = '1' AND " +
            "ta.statusid.statusid <> '3' AND ta.statusid.statusid <> '5'")
    List<T_Appeals> getForwardedAppeals(@Param("appelateid") String appelateid);

    @Query("FROM T_Appeals ta WHERE ta.appelateid.appelateid = :appelateid AND " +
            "ta.appelateactioncode.actioncode = '4' AND ta.appeallevel = '1' AND " +
            "(ta.statusid.statusid = '3' OR ta.statusid.statusid = '5')")
    List<T_Appeals> getForwardedDAppeals(@Param("appelateid") String appelateid);

    @Query("FROM T_Appeals ta WHERE ta.appelateid.appelateid = :appelateid AND " +
            "ta.appeallevel = '2' AND ta.commissionactioncode.actioncode = '7' AND " +
            "ta.forwarddate IS NULL AND ta.statusid.statusid = '6' AND " +
            "(ta.groundcode2.groundcode = '5' OR ta.groundcode2.groundcode = '3')")
    List<T_Appeals> getAppelateDirections(@Param("appelateid") String appelateid);

    @Query("FROM T_Appeals ta WHERE ta.appelateid.appelateid = :appelateid AND " +
            "(ta.appelateactioncode IS NOT NULL AND ta.appelateactioncode.actioncode <> '3')")
    List<T_Appeals> getAppelatePending(@Param("appelateid") String appelateid);

    @Query("FROM T_Appeals ta WHERE ta.appelateid.appelateid = :appelateid AND " +
            "(ta.statusid.statusid = '5' OR ta.statusid.statusid = '3') AND ta.appeallevel = '1'")
    List<T_Appeals> getAppelateDisposed(@Param("appelateid") String appelateid);
}
