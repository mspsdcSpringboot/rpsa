package com.rpsa.rpsa.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NotificationDTO {
    private String notificationid;
    private String notificationname;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date notificationdate;
}
