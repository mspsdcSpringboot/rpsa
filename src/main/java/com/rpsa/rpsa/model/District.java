package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="m_district")

public class District {

    @Id
    @Column(nullable = false)
    private String districtcode;

    @Column(nullable = false)
    private String districtname;
    private String shortname;


    @ManyToOne
    @JoinColumn(name = "statecode")
    public M_state states;
}
