package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "onlineservices")
public class OnlineServices {

    @Id
    @Column(nullable = false)
    private String slno;
    private String servicename;
    private String links;
    private String department;
    private String enclosures;
    private String online;
    private String tracklink;
    private String applyclick;
    private String trackclick;
    private String sla;
    private String dos;
    private String aas;

    @ManyToOne
    @JoinColumn(name = "departmentcode")
    private M_OnlineServicesDepartments departmentcode;
}
