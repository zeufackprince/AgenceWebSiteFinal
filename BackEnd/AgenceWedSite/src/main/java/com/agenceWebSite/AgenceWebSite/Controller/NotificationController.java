package com.agenceWebSite.AgenceWebSite.Controller;

import com.agenceWebSite.AgenceWebSite.DTO.NotifRes;
import com.agenceWebSite.AgenceWebSite.Models.Notification;
import com.agenceWebSite.AgenceWebSite.Models.OurUsers;
import com.agenceWebSite.AgenceWebSite.Models.Publication;
import com.agenceWebSite.AgenceWebSite.Repository.NotificationRepository;
import com.agenceWebSite.AgenceWebSite.Repository.PublicationRepository;
import com.agenceWebSite.AgenceWebSite.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Notification controller.
 */
@RestController 
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private PublicationRepository publicationRepository;

    /**
     * Send message response entity.
     *
     * @param request                          the request
     * @param securityContextHolder the security context persistence filter
     * @return the response entity
     */
    @PostMapping("/user/notifications/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody Notification request, @Autowired SecurityContextHolder securityContextHolder) {

        // Retrieve logged-in user using SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userName  = authentication.getName();


            Optional<OurUsers> sender = this.userRepository.findByEmail(userName);

            String message = ("Je suis Mr/Mme/Mlle" + " " + sender.get().getName() + " " + "Mon contact est :" + sender.get().getEmail() + " et " + sender.get().getTelephone() + request.getMessage());
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
            notification.setRecipients(recipientAgent);
            notification.setPublication(publicationOptional.orElse(null)); // Set publication if found
            notificationRepository.save(notification);

            return ResponseEntity.ok("Message sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
        }
    }

    //get all notification reserve to just the ADMIN
    @GetMapping("/admin/notifications/getNotification")
    @PreAuthorize("hasRole('ADMIN')")
    public List<NotifRes> getAllNotifications()
    {
        List<Notification> savedList = this.notificationRepository.findAll();
        List<NotifRes> response = new ArrayList<>();
        NotifRes note = new NotifRes();
        try {

            note.setMessage("List of all Notifications");
            note.setStatusCode(200);

            for(Notification notif : savedList){
                String recipient_mail = this.userRepository.findById(notif.getRecipients().getId()).get().getEmail();
                note.setNotif_id(notif.getId());
                note.setNot_message(notif.getMessage());
                note.setPublication_name(notif.getPublication().getTitre());
                note.setRecipient_email(recipient_mail);
                note.setCreatedAt(notif.getCreatedAt());
                response.add(note);
            }
            
        } catch (Exception e) {
            note.setMessage("Error fetching you Notifications" + e);
            note.setStatusCode(500);
            response.add(note);
        }

        return response;
    }

    //send notification to each different Agents modifier pour recuperer les info de la personne connecter 
    @GetMapping("/agent/notifications/get-by-recipientId")
    @PreAuthorize("hasRole('AGENT, ADMIN')")
    public List<NotifRes> sendNotifications(@Autowired SecurityContextHolder securityContextHolder){

        //verifies if the user is Authenticated and ritrieve it ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       
        String userName  = authentication.getName();

        Long recipient_id = this.userRepository.findByEmail(userName).get().getId();

        List<NotifRes> response = new ArrayList<>();
        NotifRes note = new NotifRes();
        try {
            List<Notification> savedList = this.notificationRepository.findNotificationByRecipientsId(recipient_id);

            note.setMessage("This are all the message you have received");
            note.setStatusCode(200);

            for(Notification notif: savedList){
                
                note.setNotif_id(notif.getId());
                note.setNot_message(notif.getMessage());
                note.setPublication_name(notif.getPublication().getTitre());
                note.setCreatedAt(notif.getCreatedAt());
                response.add(note);
            }   

        } catch (Exception e) {
            note.setMessage("Error fetching you Notifications" + e);
            note.setStatusCode(500);
            response.add(note);
        }

        return response;
    }


}

