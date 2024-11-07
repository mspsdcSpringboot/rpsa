package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VahanServiceWiseDashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serviceId;
    private String serviceName;
    private String serviceApplied;
    private String serviceApproved;
    private String servicePending;
    private String serviceRejected;
    private String serviceCode;
}
