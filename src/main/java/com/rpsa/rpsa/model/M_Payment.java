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
@Table(name = "m_payment")
public class M_Payment {
    @Id
    @Column(nullable = false)
    private String paymentid;
    private int amt;

}
