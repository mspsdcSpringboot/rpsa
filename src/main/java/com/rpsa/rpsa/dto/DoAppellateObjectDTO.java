package com.rpsa.rpsa.dto;

import com.rpsa.rpsa.model.M_Appelate;
import com.rpsa.rpsa.model.M_Designatedoffices;
import com.rpsa.rpsa.model.M_Subservices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoAppellateObjectDTO {
    private M_Subservices subservice;
    private M_Designatedoffices offices;
    private M_Appelate appelate;
}
