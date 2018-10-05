package io.crazy88.beatrix.e2e.routing.actions;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class RoutingActionsLocal extends RoutingActions {

    @Override
    public void signInToEcommRoutingServices() {
        String user = ecommProperties.getRoutingUser();
        String password = ecommProperties.getRoutingPassword();
        String authUrl = ecommProperties.getRoutingAuthUrl();

        browserNavigation.authenticateOnUrl(user, password, authUrl);
    }

}
