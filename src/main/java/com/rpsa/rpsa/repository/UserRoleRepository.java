package com.rpsa.rpsa.repository;
import com.rpsa.rpsa.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    @Query("SELECT CAST(MAX(CAST(r.roleid AS int)) AS int) FROM UserRole r")
    Integer findMaxId();


    UserRole findByRoleid(String number);
}
