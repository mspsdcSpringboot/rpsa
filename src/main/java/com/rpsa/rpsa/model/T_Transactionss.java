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

@Table(name = "t_transactions")
public class T_Transactionss {
    @Id
    @Column(nullable = false)
    private String transactionscode;
    private String transactiondetails;
    private String appeallevel;

    private String hearingtime;
    private String hearingendtime;
    private String hearingpw;
    private String vclink;
    private String venue;
    private String remarks;
    private byte[] orderdoc;




    @Temporal(TemporalType.DATE)
    @Column(name = "transactiondate")
    private Date transactiondate;
    @Temporal(TemporalType.DATE)
    @Column(name = "hearingdate")
    private Date hearingdate;

    @ManyToOne
    @JoinColumn(name = "usercode")
    private T_userlogin usercode;

    @ManyToOne
    @JoinColumn(name = "actioncode")
    private M_Action actioncode;

    @ManyToOne
    @JoinColumn(name = "appealcode")
    private T_Appeals appealcode;
}
