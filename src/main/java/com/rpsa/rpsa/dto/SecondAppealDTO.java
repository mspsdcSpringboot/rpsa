package com.rpsa.rpsa.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SecondAppealDTO {
    private String groundcode;
    private String relief;
    private String otherinfo1;
    private String appealcode;
}
