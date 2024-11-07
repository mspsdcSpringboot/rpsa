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
@Table(name = "serviceplus_khadcpayments")
public class Khadc_Payments {

    @Id
    private String slno;

    private String fyear;

    private String amount;
    private String trade;
    private String occupation;
    private String jsondata;

    @ManyToOne
    @JoinColumn(name = "applicationid")
    private Khadc_InitiatedData applicationid;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentdate;
}
