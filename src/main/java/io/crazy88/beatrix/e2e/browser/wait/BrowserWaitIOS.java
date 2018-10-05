package io.crazy88.beatrix.e2e.browser.wait;

import internal.katana.selenium.core.utils.AppiumUtils;
import io.appium.java_client.AppiumDriver;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Profile("ios")
public class BrowserWaitIOS extends BrowserWait {

    // Safari web driver doesn't support this
    public void waitForAngular() {

    }

    //When an element disappears in the DOM instead of the first to exception throws a WebdriverException (IOS Driver)
    public void waitForElementNotDisplayed(By element){
        waitUntil((Function) invisible -> {
            try {
                return Boolean.valueOf(!browser.driver().findElement(element).isDisplayed());
            } catch (NoSuchElementException var3) {
                return Boolean.valueOf(true);
            } catch (StaleElementReferenceException var4) {
                return Boolean.valueOf(true);
            } catch (WebDriverException var5) {
                return Boolean.valueOf(true);
            }
        });
    }

    @SneakyThrows
    public void waitForPageToBeLoadedCompletely() {
        AppiumUtils.changeDriverContextToNative((AppiumDriver) browser.driver());
        (new WebDriverWait(browser.driver(), 10))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//AndroidLoading| //iOSLoading")));
        AppiumUtils.changeDriverContextToWeb((AppiumDriver) browser.driver());
    }
}
