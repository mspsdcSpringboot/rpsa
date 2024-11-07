package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "serviceplus_khadcinitiateddata")
public class Khadc_InitiatedData {

    @Id
    private String applicationid;

    private String applicantname;
    private String fathername;
    private String category;
    private String email;
    private String mobile;
    private String address;
    private String district;
    private String pincode;
    private String estname;
    private String estaddress;
    private String estdistrict;
    private String estpincode;
    private String servicecode;

    private String servicename;
    private String refno;
    private String status;
    private String applyas;
    private String applyasname;
    private String submissionlocationid;
    private String submissionlocation;
    private String districtname;
    private String estdistrictname;
    private String attributedetails;



    @Temporal(TemporalType.TIMESTAMP)
    private Date submissiondate;
}
