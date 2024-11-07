package com.rpsa.rpsa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class VahanTotalDashboard {
    @Id
    private Long slNo;
    private String totalApplied;
    private String totalApproved;
    private String totalPending;
    private String totalRejected;
}
