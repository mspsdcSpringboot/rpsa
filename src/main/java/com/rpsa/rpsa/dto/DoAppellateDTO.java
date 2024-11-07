package com.rpsa.rpsa.dto;

import com.rpsa.rpsa.model.M_Appelate;
import com.rpsa.rpsa.model.M_Designatedoffices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class DoAppellateDTO {
    private String officeid;
    private String appelateid;
}
