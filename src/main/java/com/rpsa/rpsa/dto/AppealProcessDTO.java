package com.rpsa.rpsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealProcessDTO {

    private String appelateactioncode;
    private String remarks;
    private String remarkstxt;
    private String firsthearingdate;
    private String hearingtype;
    private String hearingtime1;
    private String hearingendtime1;
    private String venue1;

}
