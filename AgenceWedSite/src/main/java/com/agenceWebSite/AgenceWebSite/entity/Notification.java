package com.agenceWebSite.AgenceWebSite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "notification_recipients",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<OurUsers> recipients;

    @ManyToOne
    private Publication publication;

    private LocalDate dateUplaod;
    /**
     * Gets recipient ids.
     *
     * @return the recipient ids
     */
    public Long getRecipientIds() {

        return this.getPublication().getBienImmobilier().getUser().getId();
    }
}
