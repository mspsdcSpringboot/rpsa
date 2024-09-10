package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_appelate")

public class M_Appelate {
    @Id
    @Column(nullable = false)
    private String appelateid;

    private String officename;
    private String officelevel;
    private String submission_location_id;

    @ManyToOne
    @JoinColumn(name = "departmentcode")
    private M_department departmentcode;
}
