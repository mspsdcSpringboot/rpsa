package com.rpsa.rpsa.controller;

import com.rpsa.rpsa.model.Meetings;
import com.rpsa.rpsa.model.Notification;
import com.rpsa.rpsa.service.LoadNotifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/public")
public class NotificationController {

    @Autowired
    public LoadNotifications loadNotifications;

    //Load Notification page

    @GetMapping("/notification")
    public String getNotificationPage(Model model){

        List<Notification> notifications = loadNotifications.getAllNotifications();
        model.addAttribute("notifications", notifications);

        return "/pages/notification/notification";
    }


    @GetMapping("/viewnotificationdoc/{notificationid}")
    public ResponseEntity<byte[]> viewMeetingDoc(@PathVariable String notificationid) {
        Notification notificationData = loadNotifications.getNotificationById(notificationid);
        if (notificationData != null && notificationData.getDoc() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.setContentDispositionFormData("attachment", "notification.pdf");
            return new ResponseEntity<>(notificationData.getDoc(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
