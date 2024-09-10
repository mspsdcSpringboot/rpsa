package com.rpsa.rpsa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service_servicesid")

public class Service_Servicesid {
    @Id
    private String serviceid;
    private String servicename;
    private String sla;
    private String notified;
    private String nameattrid;
    private String mobileattrid;
    private String emailattrid;
}
