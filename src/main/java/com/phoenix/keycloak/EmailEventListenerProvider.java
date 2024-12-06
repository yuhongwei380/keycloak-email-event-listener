package com.phoenix.keycloak;

import org.keycloak.email.EmailException;
import org.keycloak.email.EmailSenderProvider;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class EmailEventListenerProvider implements EventListenerProvider {

    private final KeycloakSession session;
    private final String recipientEmail;

    public EmailEventListenerProvider(KeycloakSession session, String recipientEmail) {
        this.session = session;
        this.recipientEmail = recipientEmail;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == EventType.REGISTER) {
            RealmModel realm = session.getContext().getRealm();
            UserModel user = session.users().getUserById(realm, event.getUserId());

            if (user != null) {
                try {
                    String emailContent = generateEmailContent(user);
                    EmailSenderProvider emailSender = session.getProvider(EmailSenderProvider.class);
                    emailSender.send(realm.getSmtpConfig(), recipientEmail, "New User Registration", emailContent, null);
                } catch (EmailException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String generateEmailContent(UserModel user) {
        return "Dear Admin,\n\n" +
                "A new user has registered:\n\n" +
                "Username: " + user.getUsername() + "\n" +
                "Email: " + user.getEmail() + "\n\n" +
                "Thank you,\n" +
                "Your Keycloak System";
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        // This method is required by the EventListenerProvider interface but is not used in this example
    }

    @Override
    public void close() {
        // Any cleanup tasks
    }
}
