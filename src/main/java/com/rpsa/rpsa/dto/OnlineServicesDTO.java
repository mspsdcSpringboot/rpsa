package com.rpsa.rpsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineServicesDTO {
    private String slno;
    private String servicename;
    private String departmentcode;
    private String department;
    private String sla;
    private String dos;  // Designated Officer
    private String aas;  // Appellate Authority
    private String online;
    private String links;
    private String enclosures;
}
