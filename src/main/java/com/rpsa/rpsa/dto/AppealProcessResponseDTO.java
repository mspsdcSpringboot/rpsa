package com.rpsa.rpsa.dto;

import com.rpsa.rpsa.model.T_userlogin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AppealProcessResponseDTO {
    private String processType;
    private String title;
    private String vcLink;
    private String description;
    private String startTime;
    private String endTime;
    private String password;
    private String hearingDate;
    private T_userlogin userData;
    private String appealRefNo;

}
