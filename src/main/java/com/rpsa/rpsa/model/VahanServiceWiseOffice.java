package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class VahanServiceWiseOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long slNo;
    private String purDesc;
    private String officeApplied;
    private String officeApproved;
    private String officePending;
    private String officeRejected;
    @OneToOne
    @JoinColumn(name = "officeId")
    private VahanOffices officeCode;
}
