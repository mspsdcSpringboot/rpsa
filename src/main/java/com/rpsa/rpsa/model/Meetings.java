package com.rpsa.rpsa.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meetings")
public class Meetings {
    @Id
    @Column(nullable = false)
    private String meetingid;
    private String details;
    @Temporal(TemporalType.DATE)
    @Column(name = "meetingdate")
    private Date meetingdate;
    private byte[] doc;
}
