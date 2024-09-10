package com.rpsa.rpsa.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_subservices")
public class M_Subservices {
    @Id
    @Column(nullable = false)
    private String subservicecode;

    private String subservicename;
    private String stipulatedtime;
    @ManyToOne
    @JoinColumn(name = "servicecode")
    private M_Services servicecode;
    @ManyToOne
    @JoinColumn(name = "serviceid")
    private Service_Servicesid serviceid;
}
