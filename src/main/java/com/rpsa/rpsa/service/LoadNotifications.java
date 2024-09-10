package com.rpsa.rpsa.service;

import com.rpsa.rpsa.model.Meetings;
import com.rpsa.rpsa.model.Notification;
import com.rpsa.rpsa.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class LoadNotifications {
    @Autowired
    private NotificationRepository notificationRepository;
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAllByOrderByNotificationdateDesc();
    }


    public Notification getNotificationById(String notificationid) {
        return notificationRepository.findById(notificationid).orElse(null);
    }
}
