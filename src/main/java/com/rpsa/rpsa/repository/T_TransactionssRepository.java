package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.T_Transactionss;
import com.rpsa.rpsa.model.T_userlogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;



@Repository
public interface T_TransactionssRepository extends JpaRepository<T_Transactionss, String> {

    @Query("SELECT CAST(MAX(CAST(t.transactionscode AS int)) AS int) FROM T_Transactionss t")
    Integer findMaxUnique();

    @Query("SELECT t FROM T_Transactionss t WHERE t.appealcode.appealcode = :appealcode ORDER BY CAST(t.transactionscode AS int) DESC")
    List<T_Transactionss> findByAppealCodeOrderedByTransactionCodeDesc(@Param("appealcode") String appealcode);

//    Date d = new Date();

    @Query(value = "SELECT * FROM T_Transactionss " +
            "WHERE hearingdate >= :d " +
            "AND usercode IN (SELECT usercode FROM Users WHERE userrole <> '3') " +
            "ORDER BY hearingdate ASC",
            nativeQuery = true)
    List<T_Transactionss> getupcomingvcs(@Param("d") Date d);



    @Query(value = "SELECT * FROM T_Transactionss " +
            "WHERE hearingdate >= :d " +
            "AND usercode IN (" +
            "   SELECT usercode FROM Users " +
            "   WHERE userrole IN (SELECT roleid FROM UserRoles WHERE roleid = '3')" +
            ") " +
            "ORDER BY hearingdate ASC",
            nativeQuery = true)
    List<T_Transactionss> getcommissionupcomingvcs(@Param("d") Date d);


    List<T_Transactionss> findByUsercodeAndActioncode_ActioncodeAndVclinkIsNotNullAndHearingdateAfter(
            T_userlogin usercode,
            String actioncode,
            Date hearingdate);

}
