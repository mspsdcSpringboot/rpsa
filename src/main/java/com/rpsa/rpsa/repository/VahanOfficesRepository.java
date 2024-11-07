package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.VahanOffices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VahanOfficesRepository extends JpaRepository<VahanOffices, Long> {
}
