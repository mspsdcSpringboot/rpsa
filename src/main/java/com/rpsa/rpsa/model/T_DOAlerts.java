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
@Table(name = "t_doalerts")

public class T_DOAlerts {
    @Id
    private String alertid;
    private String alertcontent;
    @Temporal(TemporalType.DATE)
    @Column(name = "alertdate")
    private Date alertdate;

    @ManyToOne
    @JoinColumn(name = "appealcode")
    private T_Appeals appealcode;

    @ManyToOne
    @JoinColumn(name = "usercode")
    private T_userlogin usercode;

    @ManyToOne
    @JoinColumn(name = "officeid")
    private M_Designatedoffices officeid;
}
