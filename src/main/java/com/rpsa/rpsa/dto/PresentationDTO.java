package com.rpsa.rpsa.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresentationDTO {
    private String presentationid;
    private String details;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date presentationdate;
}
