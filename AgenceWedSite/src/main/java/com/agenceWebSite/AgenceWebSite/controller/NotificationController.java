package com.agenceWebSite.AgenceWebSite.controller;

import com.agenceWebSite.AgenceWebSite.entity.Notification;
import com.agenceWebSite.AgenceWebSite.entity.OurUsers;
import com.agenceWebSite.AgenceWebSite.entity.Publication;
import com.agenceWebSite.AgenceWebSite.repository.NotificationRepository;
import com.agenceWebSite.AgenceWebSite.repository.PublicationRepository;
import com.agenceWebSite.AgenceWebSite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

/**
 * The type Notification controller.
 */
@RestController // Assuming you're using Spring MVC
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private UserRepository userRepository; // Inject UserRepository for recipient lookup

    @Autowired
    private PublicationRepository publicationRepository;

    /**
     * Send message response entity.
     *
     * @param request                          the request
     * @param securityContextPersistenceFilter the security context persistence filter
     * @return the response entity
     */
    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody Notification request, @Autowired SecurityContextHolder securityContextPersistenceFilter) {

        // Retrieve logged-in user using SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            OurUsers sender = (OurUsers) authentication.getPrincipal();

            String message = request.getMessage();
            Long publicationId = request.getPublication().getId(); // Assuming publication ID is provided

            Optional<Publication> publicationOptional = publicationRepository.findById(publicationId);

            // Find recipient agent (based on publication or selection)
            OurUsers recipientAgent = null;
            if (publicationOptional.isPresent()) {
                recipientAgent = publicationOptional.get().getBienImmobilier().getUser(); // Adjust based on your relationships
            } else {
                // Logic to find recipient agent based on selection (optional)
            }

            if (recipientAgent == null) {
                return ResponseEntity.badRequest().body("Invalid publication or recipient not found");
            }

            // Create and save notification
            Notification notification = new Notification();
            notification.setSender(sender);
            notification.setMessage(message);
            notification.setRecipients((Set<OurUsers>) recipientAgent);
            notification.setPublication(publicationOptional.orElse(null)); // Set publication if found
            notificationRepository.save(notification);

            return ResponseEntity.ok("Message sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
        }
    }

}

