package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.VahanServiceWiseDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VahanServiceWiseDashboardRepository extends JpaRepository<VahanServiceWiseDashboard, Long> {
}
