package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findAllByOrderByNotificationdateDesc();
}
