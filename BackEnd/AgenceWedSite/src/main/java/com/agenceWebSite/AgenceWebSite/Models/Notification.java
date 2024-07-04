package com.agenceWebSite.AgenceWebSite.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

/**
 * The type Notification.
 */
@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private OurUsers sender;

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "notification_recipients",
    //         joinColumns = @JoinColumn(name = "notification_id"),
    //         inverseJoinColumns = @JoinColumn(name = "user_id"))
    // private Set<OurUsers> recipients;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "recipient_id")
    private OurUsers recipients;

    @ManyToOne
    private Publication publication;

    @Column(updatable = false)
    private Date createdAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = new Date();
    }

}
