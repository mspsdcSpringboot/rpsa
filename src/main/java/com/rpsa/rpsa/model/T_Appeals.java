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
@Table(name = "t_appeals")
public class T_Appeals {

    @Id
    @Column(nullable = false)
    private String appealcode;

    @Temporal(TemporalType.DATE)
    @Column(name = "firstappealdate")
    private Date firstappealdate;
    @Temporal(TemporalType.DATE)
    @Column(name = "secondappealdate")
    private Date secondappealdate;



    private String relief1;
    private String relief2;
    private String otherinfo1;
    private String otherinfo2;
    private byte[] idproof;
    private byte[] supportdoc;
    private byte[] supportdoc2;
    private byte[] supportdoc3;
    private byte[] supportdoc4;
    private byte[] supportdoc5;
    private byte[] ordercopy;
    private byte[] firstorderdoc;
    private byte[] secondorderdoc;
    private byte[] additionaldoc;
    private String daysleft;
    private String hearingtype;
    private String hearingtime1;
    private String hearingtime2;
    private String link1;
    private String link2;
    private String directions;
    private String extrasla;
    private String doaction;
    private String hearingmin1;
    private String hearingmin2;
    private String hearingpw1;
    private String hearingtitle1;
    private String hearingdesc1;
    private String hearingpw2;
    private String hearingtitle2;
    private String hearingdesc2;
    private String hearingendtime1;
    private String hearingendtime2;
    private byte[] penaltyorder;
    private byte[] form1doc;
    private String penaltyflag;
    private String paymentflag;
    private String venue1;
    private String venue2;
    private String applicationrefno;
    private String forwardremarks;
    private String refno;
    private String doremarks;




    @Temporal(TemporalType.DATE)
    @Column(name = "applicationdate")
    private Date applicationdate;
    @Temporal(TemporalType.DATE)
    @Column(name = "forwarddate")
    private Date forwarddate;
    @Temporal(TemporalType.DATE)
    @Column(name = "completiondate")
    private Date completiondate;
    @Temporal(TemporalType.DATE)
    @Column(name = "appelatedate")
    private Date appelatedate;
    @Temporal(TemporalType.DATE)
    @Column(name = "commissiondate")
    private Date commissiondate;
    @Temporal(TemporalType.DATE)
    @Column(name = "firsthearingdate")
    private Date firsthearingdate;
    @Temporal(TemporalType.DATE)
    @Column(name = "secondhearingdate")
    private Date secondhearingdate;
    @Temporal(TemporalType.DATE)
    @Column(name = "lastactiondate")
    private Date lastactiondate;

    @ManyToOne
    @JoinColumn(name = "usercode")
    private T_userlogin usercode;
    @ManyToOne
    @JoinColumn(name = "dosubordinate")
    private T_userlogin dosubordinate;
    @ManyToOne
    @JoinColumn(name = "statusid")
    private M_Status statusid;

    @ManyToOne
    @JoinColumn(name = "groundcode1")
    private M_AppealGround groundcode1;
    @ManyToOne
    @JoinColumn(name = "groundcode2")
    private M_AppealGround groundcode2;

    @ManyToOne
    @JoinColumn(name = "forwardusercode")
    private T_userlogin forwardusercode;
    @ManyToOne
    @JoinColumn(name = "appelateactioncode")
    private M_Action appelateactioncode;
    @ManyToOne
    @JoinColumn(name = "forwardactioncode")
    private M_Action forwardactioncode;
    @ManyToOne
    @JoinColumn(name = "commissionactioncode")
    private M_Action commissionactioncode;
    @ManyToOne
    @JoinColumn(name = "appelateid")
    private M_Appelate appelateid;

    @ManyToOne
    @JoinColumn(name = "officeid")
    private M_Designatedoffices officeid;
    @ManyToOne
    @JoinColumn(name = "subservicecode")
    private M_Subservices subservicecode;

    private String appeallevel;
    
}
