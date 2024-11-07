package com.rpsa.rpsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transport_data_dump")
public class TransportDataDump {
    @Id
    private String slno;
    private String servicename;
    private String submissionlocationid;
    private String delivered;
    private String applied;
    private String pending;
    private String rejected;
    private String appl_type;
}
