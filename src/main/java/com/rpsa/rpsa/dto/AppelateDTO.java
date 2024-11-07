package com.rpsa.rpsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppelateDTO {
    private String appelatename;
    private String appelateid;
    private String officelevel;
    private String departmentcode;
}
