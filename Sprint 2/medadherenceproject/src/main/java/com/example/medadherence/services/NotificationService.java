package com.example.medadherence.services;

import com.example.medadherence.models.Notification;
import com.example.medadherence.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Get all notifications
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Add a new notification
    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    // Get a notification by ID
    public Notification getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.orElse(null);
    }

    // Delete a notification by ID
    public String deleteNotification(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return "Notification deleted successfully";
        }
        return "Notification not found";
    }
}