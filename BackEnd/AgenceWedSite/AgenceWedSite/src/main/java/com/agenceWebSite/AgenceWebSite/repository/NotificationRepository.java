package com.agenceWebSite.AgenceWebSite.repository;

import com.agenceWebSite.AgenceWebSite.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Notification repository.
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
