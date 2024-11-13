package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Designatedoffices;
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

    List<T_Appeals> findAllByOfficeid(M_Designatedoffices officeid);

    @Query("FROM T_Appeals ta WHERE ta.forwardusercode.usercode = :usercode AND " +
            "ta.statusid.statusid = '1' AND forwardactioncode is null and forwarddate is null")
    List<T_Appeals> getSoAppeals(@Param("usercode") String usercode);

    @Query("FROM T_Appeals ta WHERE ta.forwardusercode.usercode = :usercode AND " +
            "ta.forwardactioncode is not null")
    List<T_Appeals> getForwardedProcessedSoAppeals(@Param("usercode") String usercode);

    @Query("FROM T_Appeals ta WHERE ta.forwardusercode.usercode = :usercode AND " +
            "ta.forwardactioncode.actioncode <> '2' and forwardactioncode.actioncode <> '3'")
    List<T_Appeals> getSoPending(@Param("usercode") String usercode);

    @Query(value = "SELECT * FROM T_Appeals WHERE forwardusercode = :forwardusercode " +
            "AND forwardactioncode IS NULL " +
            "AND forwarddate IS NULL " +
            "AND appeallevel = '1'",
            nativeQuery = true)
    List<T_Appeals> getAllFAppeals(@Param("forwardusercode") String forwardusercode);

    @Query(value = "SELECT * FROM T_Appeals WHERE forwardusercode = :forwardusercode " +
            "AND forwardactioncode IS NOT NULL",
            nativeQuery = true)
    List<T_Appeals> getAllFprocessedAppeals(@Param("forwardusercode") String forwardusercode);

    @Query(value = "SELECT * FROM T_Appeals WHERE forwardusercode = :forwardusercode " +
            "AND forwardactioncode <> '2' " +
            "AND forwardactioncode <> '3'",
            nativeQuery = true)
    List<T_Appeals> getfpending(@Param("forwardusercode") String forwardusercode);

    @Query(value = "SELECT * FROM T_Appeals WHERE forwardusercode = :forwardusercode " +
            "AND (statusid = '5' OR statusid = '3')",
            nativeQuery = true)
    List<T_Appeals> getfdisposed(@Param("forwardusercode") String forwardusercode);

    List<T_Appeals> findAllByDosubordinate(T_userlogin user);


    @Query(value = "SELECT " +
            "COUNT(appealcode) AS appealcount, " +
            "(SELECT COUNT(appealcode) FROM rpsa.t_appeals WHERE statusid = '5' AND appelateid = :appelateid AND appeallevel = '1') AS disposed, " +
            "(SELECT COUNT(appealcode) FROM rpsa.t_appeals WHERE statusid = '3' AND appelateid = :appelateid AND appeallevel = '1') AS rejected, " +
            "(SELECT COUNT(appealcode) FROM rpsa.t_appeals WHERE statusid <> '5' AND statusid <> '3' AND statusid <> '8' AND appelateid = :appelateid AND CAST(daysleft AS INTEGER) >= 0 AND appeallevel = '1') AS pendingwithin, " +
            "(SELECT COUNT(appealcode) FROM rpsa.t_appeals WHERE statusid <> '5' AND statusid <> '3' AND statusid <> '8' AND appelateid = :appelateid AND CAST(daysleft AS INTEGER) < 0 AND appeallevel = '1') AS pendingbeyond, " +
            "(SELECT COUNT(appealcode) FROM rpsa.t_appeals WHERE statusid = '8' AND appelateid = :appelateid AND appeallevel = '1') AS penalty " +
            "FROM rpsa.t_appeals " +
            "WHERE appelateid = :appelateid AND appeallevel = '1'",
            nativeQuery = true)
    Object[] findAppealStatisticsByAppelateId(@Param("appelateid") String appelateid);


    @Query(value = "SELECT " +
            "a.officename, " +
            "a.appelateid, " +
            "COUNT(ta.appelateid) AS applied, " +
            "COUNT(CASE WHEN CAST(ta.statusid AS INTEGER) NOT IN (5,3,8) AND CAST(ta.daysleft AS INTEGER) >= 0 THEN ta.appelateid END) AS pendingwithin, " +
            "COUNT(CASE WHEN CAST(ta.statusid AS INTEGER) NOT IN (5,3,8) AND CAST(ta.daysleft AS INTEGER) < 0 THEN ta.appelateid END) AS pendingbeyond, " +
            "COUNT(CASE WHEN CAST(ta.statusid AS INTEGER) = 5 THEN ta.appelateid END) AS disposed, " +
            "COUNT(CASE WHEN CAST(ta.statusid AS INTEGER) = 3 THEN ta.appelateid END) AS rejected " +
            "FROM rpsa.m_appelate a " +
            "LEFT OUTER JOIN rpsa.t_appeals ta ON ta.appelateid = a.appelateid " +
            "WHERE a.appelateid = :appelateid AND appeallevel = '1' " +
            "GROUP BY a.appelateid, a.officename, a.appelateid " +
            "ORDER BY a.appelateid",
            nativeQuery = true)
    List<Object[]> findAppelateStatisticsByAppelateId(@Param("appelateid") String appelateid);

    T_Appeals findByRefnoAndUsercode(String refno, T_userlogin usercode);
}
