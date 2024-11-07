package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VahanOffices")
public class VahanOffices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long officeId;
    private String officeName;
}
