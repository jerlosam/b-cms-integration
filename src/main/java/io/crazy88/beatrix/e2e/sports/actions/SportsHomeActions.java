package io.crazy88.beatrix.e2e.sports.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import io.crazy88.beatrix.e2e.sports.components.SportsHomeComponent;

@Component
public class SportsHomeActions {

    private static final String SPORTS_URL_PATH = "sports";

    @Autowired
    private SportsHomeComponent sportsHomeComponent;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Autowired
    private E2EProperties e2eProperties;

    @Autowired
    private BrowserNavigation browserNavigation;

    public boolean isHomeComponentDisplayed() {
        return sportsHomeComponent.isHomeComponentDisplayed();
    }

    public void navigateToSportsHomePageUrl() {
        playerNavigationActions.enterSite();
        final String sportsUrl = e2eProperties.getPlayerHomeUrl() + SPORTS_URL_PATH;

        browserNavigation.navigateToUrl(sportsUrl);
    }
}
