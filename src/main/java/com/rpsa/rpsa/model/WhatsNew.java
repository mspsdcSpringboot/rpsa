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
@Table(name = "whatsnew")
public class WhatsNew {
    @Id
    @Column(nullable = false)
    private String whatsnewid;

    private String heading;
    private String newbody;
    @Temporal(TemporalType.DATE)
    @Column(name = "whatsnewdate")
    private Date whatsnewdate;
}
