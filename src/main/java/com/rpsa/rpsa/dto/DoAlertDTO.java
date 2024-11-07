package com.rpsa.rpsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoAlertDTO {
    private String officeid;
    private String appealcode;
    private String email;
    private String content;
}
