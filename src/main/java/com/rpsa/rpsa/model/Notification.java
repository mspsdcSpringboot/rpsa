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
@Table(name = "notifications")
public class Notification {
    @Id
    @Column(nullable = false)
    private String notificationid;

    private String notificationname;
    private String title;
    @Temporal(TemporalType.DATE)
    @Column(name = "notificationdate")
    private Date notificationdate;
    private byte[] doc;
}
