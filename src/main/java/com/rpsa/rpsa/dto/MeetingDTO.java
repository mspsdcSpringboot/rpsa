package com.rpsa.rpsa.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MeetingDTO {
    private String meetingid;
    private String details;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date meetingdate;
}
