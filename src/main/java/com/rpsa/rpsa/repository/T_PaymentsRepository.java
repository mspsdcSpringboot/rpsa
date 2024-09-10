package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface T_PaymentsRepository extends JpaRepository<Visitors.T_Payments, String> {
}
