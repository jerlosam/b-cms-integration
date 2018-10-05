package io.crazy88.beatrix.e2e.browser.navigation;

import internal.katana.selenium.core.browser.BrowserVendor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Profile("internet_explorer")
public class BrowserNavigationInternetExplorer extends BrowserNavigation {

    public void scrollToBottom() {
        int times = 0;
        long scroll = (Long) ((JavascriptExecutor)browser.driver()).executeScript(" return document.body.scrollHeight");
        long previousScroll = 0L;
        do {
            previousScroll = scroll;
            ((JavascriptExecutor)browser.driver()).executeScript("window.scrollTo(0,5000);");
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
            }
            scroll = (Long) ((JavascriptExecutor)browser.driver()).executeScript(" return document.body.scrollHeight");
            times++;
        }while (scroll != previousScroll && times < 5);
    }

    public void  navigateWithElement(By element) {
        browserWaits.waitForElement(element);
        browser.driver().findElement(element).click();
        browserWaits.waitForAngular();
    }
}