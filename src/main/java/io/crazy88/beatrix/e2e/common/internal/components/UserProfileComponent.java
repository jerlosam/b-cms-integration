package io.crazy88.beatrix.e2e.common.internal.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class UserProfileComponent {

    private static final By USER_PROFILE = By.cssSelector("bx-user-profile");

    private static final By LOGOUT_BUTTON = By.cssSelector("bx-user-profile button");

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;

    public void logout(){
        findElement(USER_PROFILE).click();
        findElement(LOGOUT_BUTTON).click();
    }

    private WebElement findElement(By element) {
        browserWait.waitForElement(element);
        return browser.driver().findElement(element);
    }

}
