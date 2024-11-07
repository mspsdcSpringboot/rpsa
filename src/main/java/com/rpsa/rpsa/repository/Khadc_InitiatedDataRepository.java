package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Khadc_InitiatedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Khadc_InitiatedDataRepository extends JpaRepository<Khadc_InitiatedData, String> {
    List<Khadc_InitiatedData> findAllByServicecode(String c);
}
