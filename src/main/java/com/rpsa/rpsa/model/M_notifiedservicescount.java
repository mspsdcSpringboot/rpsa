package com.rpsa.rpsa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_notifiedservicescount")
public class M_notifiedservicescount {

    @Id
    @Column(nullable = false)
    private String notifiedservices;
    private String scount;
}
