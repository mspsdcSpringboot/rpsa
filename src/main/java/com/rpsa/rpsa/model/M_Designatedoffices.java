package com.rpsa.rpsa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Retention;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_designatedoffices")
public class M_Designatedoffices {
    @Id
    private String officeid;
    private String officename;
    private String officername;
    private String email;
    private String mobile;
    private String created;
    private String username;
    private String submission_location_id;

    public static M_Designatedoffices findByOfficeid(String designatedofficer) {
        return findByOfficeid(designatedofficer);
    }
}
