package com.rpsa.rpsa.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SubmitServiceDTO {
    private String subservicecode;
    private String subservicename;
    private String stipulatedtime;
    private String servicecode;
    private String servicename;

}
