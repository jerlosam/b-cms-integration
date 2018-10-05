package io.crazy88.beatrix.e2e.player.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class NotificationsComponent {

    public static final By NOTIFICATION = By.cssSelector(".bx-notification-container");
    public static final By SUCCESS_NOTIFICATION = By.cssSelector(".bx-notification-container figure.success");

    @Autowired
    private BrowserWait browserWaits;

    @Autowired
    private BrowserValidation browserValidations;

    @Autowired
    private BrowserManager browser;

    public void waitForNotificationsNotDisplayed() {
        browserWaits.waitForElementNotDisplayed(NOTIFICATION);
    }

    public void waitForNotification() {
        browserWaits.waitForElement(NOTIFICATION);
    }

    public Boolean isSuccessNotificationDisplayed() {
        return browserValidations.isElementDisplayed(SUCCESS_NOTIFICATION);
    }
}
