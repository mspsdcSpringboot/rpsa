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
@Table(name = "tasktype")
public class TaskType {
    @Id
    @Column(nullable = false)
    private String tasktypeid;

    private String tasktypename;
}
