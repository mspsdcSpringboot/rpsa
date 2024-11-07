package com.rpsa.rpsa.repository;

import com.rpsa.rpsa.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    @Query("SELECT CAST(MAX(CAST(n.notificationid AS int)) AS int) FROM Notification n")
    Integer findMaxId();
    List<Notification> findAllByOrderByNotificationdateDesc();
}
