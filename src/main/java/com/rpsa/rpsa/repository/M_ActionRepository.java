package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface M_ActionRepository extends JpaRepository<M_Action, String> {

    @Query("from M_Action where flag = '0' and actioncode <> '2'")
    List<M_Action> findAllByFlagAndActioncode();

//    @Query(value = "SELECT * FROM M_Action WHERE flag = '0' AND actioncode <> '2'", nativeQuery = true)
//    List<M_Action> findAllByFlagAndActioncode();

}
