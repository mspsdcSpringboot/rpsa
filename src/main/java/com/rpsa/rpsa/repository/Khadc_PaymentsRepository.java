package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Khadc_InitiatedData;
import com.rpsa.rpsa.model.Khadc_Payments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface Khadc_PaymentsRepository extends JpaRepository<Khadc_Payments, String> {

    List<Khadc_Payments> findByApplicationid_Applicationid(String applicationid);

    Page<Khadc_Payments> findAll(Pageable pageable);

    List<Khadc_Payments> findByPaymentdateBetween(Timestamp startDate, Timestamp endDate);
}
