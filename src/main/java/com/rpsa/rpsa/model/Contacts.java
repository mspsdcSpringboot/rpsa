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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts")
public class Contacts {
    @Id
    @Column(nullable = false)
    private String contactid;
    private String contactname;
    private String phone;
    private String email;
    private String emailtxt;
    private String designation;
}
