package io.crazy88.beatrix.e2e.customermanager.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SearchPlayerComponent {

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserForm browserForms;

    @Autowired
    private BrowserWait browserWaits;

    @Autowired
    private BrowserNavigation browserNavigation;
    
    private static final By EMAIL_FIELD = By.id("profileSearch-email");
    private static final By ADVANCE_SEARCH_FORM = By.cssSelector("bx-profile-search-form");
    private static final By QUICK_SEARCH_FIELD = By.id("profile-search-input");
    private static final By QUICK_SEARCH_BUTTON = By.cssSelector(".split-btn .custom-cta");

    private static final By RESULTS_TABLE = By.xpath("//span[text()[contains(.,'@')]]/ancestor::table");
    private static final String PLAYER_WITH_EMAIL_AND_BRAND = "//span[text()='%s']/ancestor::tr//span[text()='%s']/ancestor::tr";

    public void searchPlayerByEmail(String email) {
        browserForms.fillTextField(EMAIL_FIELD,email);
        browserNavigation.scrollToBottom();
        browserForms.submitFormInContainer(ADVANCE_SEARCH_FORM);
        browserNavigation.scrollToTop();
    }

    public boolean isPlayerDisplayedOnResultsTable(String playerEmail, String brandCode) {
        browserWaits.waitForElement(RESULTS_TABLE);
        return !(browser.driver().findElements(playerRowLocator(playerEmail, brandCode)).isEmpty());
    }

    @SneakyThrows
    public void navigateToPlayerFromResultsTable(String playerEmail, String brandCode){
        browserWaits.waitForElement(RESULTS_TABLE);
        if(isPlayerDisplayedOnResultsTable(playerEmail, brandCode)) {
            browser.driver().findElements(playerRowLocator(playerEmail, brandCode)).get(0).click();
        }
        TimeUnit.SECONDS.sleep(8);
    }

    public void quickSearchPlayer(String player){
        browserForms.fillTextField(QUICK_SEARCH_FIELD, player);
        browser.driver().findElement(QUICK_SEARCH_BUTTON).click();
    }

    private By playerRowLocator(String email, String brandCode){
        return By.xpath(String.format(PLAYER_WITH_EMAIL_AND_BRAND,email.toLowerCase(),brandCode.toUpperCase()));
    }
}
