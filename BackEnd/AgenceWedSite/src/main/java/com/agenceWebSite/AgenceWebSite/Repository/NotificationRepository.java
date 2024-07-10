package com.agenceWebSite.AgenceWebSite.Repository;

import com.agenceWebSite.AgenceWebSite.Models.Notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Notification repository.
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findNotificationByRecipientsId(Long recipients_id);
}
