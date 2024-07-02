package com.agenceWebSite.AgenceWebSite.Controller;

import com.agenceWebSite.AgenceWebSite.Models.Notification;
import com.agenceWebSite.AgenceWebSite.Models.OurUsers;
import com.agenceWebSite.AgenceWebSite.Models.Publication;
import com.agenceWebSite.AgenceWebSite.Repository.NotificationRepository;
import com.agenceWebSite.AgenceWebSite.Repository.PublicationRepository;
import com.agenceWebSite.AgenceWebSite.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The type Notification controller.
 */
@RestController // Assuming you're using Spring MVC
@RequestMapping("/api")
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
     * @param securityContextHolder the security context persistence filter
     * @return the response entity
     */
    @PostMapping("/agent/notifications/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody Notification request, @Autowired SecurityContextHolder securityContextHolder) {

        // Retrieve logged-in user using SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userName  = authentication.getName();


            Optional<OurUsers> sender = this.userRepository.findByEmail(userName);

            String message = ("Je suis Mr/Mme/Mlle" + " " + sender.get().getName() + "Mon contact est :" + sender.get().getEmail() + " et " + sender.get().getTelephone() + request.getMessage());
            Long publicationId = request.getPublication().getId(); // Assuming publication ID is provided

            Optional<Publication> publicationOptional = publicationRepository.findById(publicationId);

            // Find recipient agent
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
            notification.setSender(sender.get());
            notification.setMessage(message);
            notification.setRecipients(Collections.singleton(recipientAgent));
            notification.setPublication(publicationOptional.orElse(null)); // Set publication if found
            notificationRepository.save(notification);

            return ResponseEntity.ok("Message sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
        }
    }

    @GetMapping("/agent/notifications/getNotification")
    public List<Notification> getNotifications()
    {
        return this.notificationRepository.findAll();
    }


}

