package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.RtoDistrict;
import org.hibernate.Internal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RtoDistrictRepository extends JpaRepository<RtoDistrict, String> {
}
