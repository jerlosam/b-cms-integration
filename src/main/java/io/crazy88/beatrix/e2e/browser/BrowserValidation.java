package io.crazy88.beatrix.e2e.browser;

import internal.katana.selenium.BrowserManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class BrowserValidation {

    @Autowired
    private BrowserManager browser;

    public boolean isElementDisplayedInContainer(By container, By element) {
        final List<WebElement> links = browser.driver().findElement(container).findElements(element);
        return !links.isEmpty() && links.get(0).isDisplayed();
    }

    public boolean isElementDisplayed(By element) {
        return browser.driver().findElement(element).isDisplayed();
    }

    public boolean isElementDisplayedWaitFor(final By locator, final long seconds) {

        boolean isDisp = false;

        browser.driver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);

        try {
            isDisp = browser.driver().findElement(locator).isDisplayed();
        } catch (final NoSuchElementException e) {
        }

        // 10 seconds by default
        browser.driver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return isDisp;
    }
}
