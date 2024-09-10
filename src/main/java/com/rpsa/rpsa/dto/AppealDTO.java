package com.rpsa.rpsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealDTO {


    private String service;
    private String appelateid;
    private String subservice;
    private String designatedofficer;
    private String applicationrefno;
    private String applicationdate;
    private String groundcode;
    private String relief1;
    private String otherinfo1;
    private String hearingtype;
    private String declaration;


}
