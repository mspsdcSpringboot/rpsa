package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_AppealGround;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface M_AppealGroundRepository extends JpaRepository<M_AppealGround, String> {

    List<M_AppealGround> findAllByAppealno(String number);
}
