package com.rpsa.rpsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppealToAppellateDTO {
    private long appealCount;
    private long submitted;
    private long disposed;
    private long rejected;
    private long pendingWithin;
    private long pendingBeyond;
    private long penalty;
}
