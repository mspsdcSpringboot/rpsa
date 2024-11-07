package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Appelate;
import com.rpsa.rpsa.model.M_Designatedoffices;
import com.rpsa.rpsa.model.T_userlogin;
import com.rpsa.rpsa.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface T_userloginRepository extends JpaRepository<T_userlogin, String> {
    @Query("SELECT CAST(MAX(CAST(u.usercode AS int)) AS int) FROM T_userlogin u")
    Integer findMaxId();

    T_userlogin findUserByContact(String contact);


    T_userlogin findByUsername(String userName);

    @Query("from T_userlogin ul where ul.userrole.roleid = '6' and ul.appelateid.appelateid = :appelateid and ul.active = 'Y'")
    List<T_userlogin> findActiveUsersByAppelateId(@Param("appelateid") String appelateid);

    List<T_userlogin> findByOfficeidAndUserrole(M_Designatedoffices officeid, UserRole role);

    List<T_userlogin> findByAppelateidAndUserrole(M_Appelate appelateid, UserRole role);

    List<T_userlogin> findAllByOfficeidAndUserrole_roleid(M_Designatedoffices officeid, String number);
}
