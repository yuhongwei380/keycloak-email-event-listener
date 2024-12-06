package com.phoenix.keycloak;

import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class EmailEventListenerProviderFactory implements EventListenerProviderFactory {

    private static final String PROVIDER_ID = "email-event-listener";
    private static String recipientEmail;

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        if (recipientEmail == null) {
            recipientEmail = System.getenv("RECIPIENT_EMAIL");
            if (recipientEmail == null) {
                throw new IllegalStateException("RECIPIENT_EMAIL environment variable not set");
            }
        }
        return new EmailEventListenerProvider(session, recipientEmail);
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Initialization if needed
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Post initialization if needed
    }

    @Override
    public void close() {
        // Cleanup if needed
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
