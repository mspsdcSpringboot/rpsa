package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.T_Transactionss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface T_TransactionssRepository extends JpaRepository<T_Transactionss, String> {

    @Query("SELECT CAST(MAX(CAST(t.transactionscode AS int)) AS int) FROM T_Transactionss t")
    Integer findMaxUnique();

    @Query("SELECT t FROM T_Transactionss t WHERE t.appealcode.appealcode = :appealcode ORDER BY CAST(t.transactionscode AS int) DESC")
    List<T_Transactionss> findByAppealCodeOrderedByTransactionCodeDesc(@Param("appealcode") String appealcode);




}
