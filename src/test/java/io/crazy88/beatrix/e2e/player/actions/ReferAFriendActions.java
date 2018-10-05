package io.crazy88.beatrix.e2e.player.actions;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.clients.dto.Referral;
import io.crazy88.beatrix.e2e.player.components.ReferAFriendComponent;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.crazy88.beatrix.e2e.clients.ReferralClient;

import java.util.Optional;
import java.util.UUID;


@Component
public class ReferAFriendActions {

    @Autowired
    private E2EProperties properties;

    @Autowired
    private BrowserWait browserWaits;

    @Autowired
    protected BrowserNavigation browserNavigation;

    @Autowired
    private Reporter reporter;

    @Autowired
    private ReferAFriendComponent raf;

    @Autowired
    protected PlayerNavigationActions playerNavigationActions;

    @Autowired
    private ReferralClient referralClient;

    private static final By SHARING_LINKS = By.cssSelector("bx-raf-links");

    private static final By REFER_A_FRIEND_CTA_BUTTON = By.cssSelector("article.raf-intro > section.intro-section > a.custom-cta");

    public void waitForSharingLinksDisplayed() {
        browserWaits.waitForElement(SHARING_LINKS);
    }

    public void waitForCtaBtn() {
        browserWaits.waitForElement(REFER_A_FRIEND_CTA_BUTTON);
    }

    public void navigateToReferPage() {
        browserNavigation.navigateToUrl(properties.getPlayerReferAFriendUrl());
        if (playerNavigationActions.isLoggedIn()) {
            waitForSharingLinksDisplayed();
        } else {
            waitForCtaBtn();
        }
        reporter.info(String.format("Navigated to refer page"));
    }

    public boolean isSharingLinksDisplayed() {
        return raf.isSharingLinksDisplayed();
    }

    public boolean isRAFButtonDisplayed() {
        return raf.isRAFButtonDisplayed();
    }

    public String copyLink() {
        return raf.copyLink();
    }

    public Optional<UUID> getReferrerId(UUID refereeId) {
        Optional<UUID> referrerId;
        try {
            referrerId = Optional.ofNullable(referralClient.getProfile(refereeId).getReferredByProfile()).map(i -> i.getProfileId());

        } catch (RuntimeException exception) {
            throw exception;
        }

        return referrerId;
    }

    public String getReferrerTrackingCode(UUID referrerId) {
        String referrerToken;
        try {
            referrerToken = referralClient.getProfile(referrerId).getTrackingCode();

        } catch (RuntimeException exception) {
            throw exception;
        }

        return referrerToken;
    }

    public void addRefereeAffiliateWithExternalProfileId(UUID referrerId, Long externalProfileId, String brandCode) {

        try {
            referralClient.addRefereeAffiliateWithExternalProfileId(referrerId, externalProfileId, brandCode);

        } catch (RuntimeException exception) {
            throw exception;
        }

    }

}
