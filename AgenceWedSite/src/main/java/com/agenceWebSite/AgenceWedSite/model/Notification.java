package com.agenceWebSite.AgenceWedSite.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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
    private User sender;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "notification_recipients",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> recipients;
}
