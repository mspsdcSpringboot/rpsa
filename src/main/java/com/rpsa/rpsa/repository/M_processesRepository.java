package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Processes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface M_processesRepository extends JpaRepository<M_Processes, String> {
    @Query(value = "SELECT processid FROM rpsa.process_role where roleid = :roleid", nativeQuery = true)
    List<String> getProcessessByRoleId(@Param("roleid") String roleid);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rpsa.process_role WHERE roleid = :roleid", nativeQuery = true)
    void deleteUsingRoleid(@Param("roleid") String roleid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO rpsa.process_role (processid, roleid) VALUES (:processId, :roleId)", nativeQuery = true)
    void insertProcessRole(@Param("processId") String processId, @Param("roleId") String roleId);


//    @Query("SELECT p FROM M_Processes p WHERE p.processid = :processid ORDER BY p.processid ASC")
//    List<M_Processes> findAllByProcessidOrderByProcessidAsc(@Param("processid") String processid);
}
