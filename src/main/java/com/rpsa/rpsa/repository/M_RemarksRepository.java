package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.M_Remarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface M_RemarksRepository extends JpaRepository<M_Remarks, String> {

}
