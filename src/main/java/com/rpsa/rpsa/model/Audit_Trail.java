package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Audit_Trail {
    @Id
    @Column(nullable = false)
    private String auditid;

    private String ipaddress;
    private String url;
    private String actions;

    @Temporal(TemporalType.DATE)
    @Column(name = "datatime")
    private Date datatime;

    @ManyToOne
    @JoinColumn(name = "usercode")
    private T_userlogin usercode;
}
