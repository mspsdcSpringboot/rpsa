package com.rpsa.rpsa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveAppellateDTO {
    private String subservicecode;
    private String officeid;
    private String appelateid;
}
