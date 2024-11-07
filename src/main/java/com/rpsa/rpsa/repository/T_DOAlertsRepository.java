package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Designatedoffices;
import com.rpsa.rpsa.model.T_DOAlerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface T_DOAlertsRepository extends JpaRepository<T_DOAlerts, String> {
    List<T_DOAlerts> findAllByOfficeid(M_Designatedoffices officeid);

    @Query("SELECT CAST(MAX(CAST(n.alertid AS int)) AS int) FROM T_DOAlerts n")
    Integer findMaxId();
}
