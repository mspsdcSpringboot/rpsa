package com.rpsa.rpsa.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDetailsDto {
    private long totalSubmitted;
    private long totalDelivered;
    private long totalRejected;
    private long withApplicant;
    private long withOfficialWithinSLA;
    private long withOfficialBeyondSLA;
    private long totalPending;
}
