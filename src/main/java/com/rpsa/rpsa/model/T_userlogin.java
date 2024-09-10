package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class T_userlogin {
    @Id
    private String usercode;

    private String username;
    private String userpassword;

    private String fullname;
    private String mname;
    private String lname;

    private String address;

    private String contact;
    private String designation;
    private String active;
    private String email;
    @Transient
    private String captcha;

    @Transient
    private String hiddenCaptcha;

    @Transient
    private String realCaptcha;
//    private String aadhaar;


    @ManyToOne
    @JoinColumn(name = "roleid")
    private UserRole userrole;

    @ManyToOne
    @JoinColumn(name = "districtcode")
    private District districtcode;

    @ManyToOne
    @JoinColumn(name = "departmentcode")
    private M_department departmentcode;

    @ManyToOne
    @JoinColumn(name = "officeid")
    private M_Designatedoffices officeid;

    @ManyToOne
    @JoinColumn(name = "appelateid")
    private M_Appelate appelateid;
}
