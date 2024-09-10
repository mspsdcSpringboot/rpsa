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
@Table(name = "visitors")
public class Visitors {

    @Id
    private String visitorid;
    private String total;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "t_payments")

    public static class T_Payments {
        @Id
        @Column(nullable = false)
        private String transactioncode;

        private String amount;

        private String paymentstatus;
        private String sentparameters;
        private String responseparameters;
        private String grn;
        private String overallstatus;

        @Temporal(TemporalType.DATE)
        @Column(name = "entrydate")
        private Date entrydate;

        @ManyToOne
        @JoinColumn(name = "appealcode")
        private T_Appeals appealcode;

        @ManyToOne
        @JoinColumn(name = "usercode")
        private T_userlogin usercode;

    }
}
