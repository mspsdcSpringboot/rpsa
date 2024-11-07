package com.rpsa.rpsa.model;

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
@Table(name = "m_processes_new")


public class M_Processes {
    @Id
    private String processid;
    private String processname;
    private String description;
    private String pageurl;
}
