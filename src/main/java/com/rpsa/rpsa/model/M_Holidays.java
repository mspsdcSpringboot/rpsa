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
@Table(name = "m_holidays")
public class M_Holidays {
    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "holidaydate")
    private Date holidaydate;

    private String holidayname;
}
