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
@Table(name = "initiateddata")
public class InitiatedData {
    @Id
    private String applicationid;
    private String initiateddata;
    private String applicationrefno;
    private String submissionlocation;
    private String submission_location_id;
    private String submission_mode;
    private String department_id;
    private String department_name;
    private String appliedby;
    private String lasttransactionid;
    private String waitingtime;
    private String pendingstatus;
    private String triggered;
    private String fullname;
    private String email;
    private String mobile;
    private String checkflag;
    private String sla;
    private String daystaken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submissiondate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date slafrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "serviceid")
    public Service_Servicesid serviceid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status")
    public TaskType tasktypeid;

}
