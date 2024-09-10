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
@Table(name = "presentations")
public class Presentations {
    @Id
    @Column(nullable = false)
    private String presentationid;

    private String details;
    @Temporal(TemporalType.DATE)
    @Column(name = "presentationdate")
    private Date presentationdate;
    private byte[] doc;
}
