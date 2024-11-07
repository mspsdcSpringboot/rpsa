package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.VahanTotalDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VahanTotalDashboardRepository extends JpaRepository<VahanTotalDashboard, Long> {
}
