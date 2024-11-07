package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.OnlineServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OnlineServicesRepository extends JpaRepository<OnlineServices, String> {

    @Query("SELECT CAST(MAX(CAST(m.slno AS int)) AS int) FROM OnlineServices m")
    Integer findMaxId();

    List<OnlineServices> findByOnlineIn(List<String> onlineValues);

    @Modifying
    @Transactional
    @Query(value = "UPDATE rpsa.onlineservices SET applyclick = CAST(applyclick AS INTEGER) + 1 WHERE slno = :slno", nativeQuery = true)
    String incrementApplyClick(@Param("slno") String slno);
}
