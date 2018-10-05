package io.crazy88.beatrix.e2e.browser.navigation;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.appium.java_client.AppiumDriver;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.player.components.HeaderComponent;
import lombok.SneakyThrows;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

@Component
@Profile({"defaultBrowser", "android"})
public class BrowserNavigation {

    @Autowired BrowserManager browser;

    @Autowired BrowserWait browserWaits;

    @Autowired BrowserForm browserForms;

    @Autowired
    private Reporter reporter;

    private Integer headerHeight = null;

    @SneakyThrows
    public void authenticateOnUrl(String user, String password, String url){
        String encodedUser = URLEncoder.encode(user, "UTF-8");
        String encodedPassword = URLEncoder.encode(password, "UTF-8");
        String urlWithBasicAuth = String.format(url,encodedUser,encodedPassword);
        browser.driver().get(urlWithBasicAuth);

    }
    public void navigateToUrl(String Url) {
        browser.driver().get(Url);
        browserWaits.waitForUrl(Url);
        browserWaits.waitForAngular();
    }

    public void navigateToUrlWithRedirection(String url, String relativePath){
        reporter.debug("Navigating to " + url);
        browser.driver().get(url);
        reporter.debug("Redirected to " + browser.driver().getCurrentUrl());
        browserWaits.waitForUrl(url + relativePath);
        browserWaits.waitForAngular();
    }

    public void navigateToUrlWithRedirectionContaining(String url, String redirectedUrl){
        reporter.debug("Navigating to " + url);
        browser.driver().get(url);
        reporter.debug("Redirected to " + browser.driver().getCurrentUrl());
        reporter.debug("Waiting for: " + redirectedUrl);
        browserWaits.waitForUrlContaining(redirectedUrl);
        browserWaits.waitForAngular();
    }

    public void navigateWithElement(By element) {
        navigateWithElement(element, true);
    }

    public void navigateWithElement(By element, boolean getFocus) {
        String currentUrl = browser.driver().getCurrentUrl();
        browserWaits.waitForElement(element);
        if(getFocus) {
            browser.driver().findElement(element).sendKeys("");
        }
        browser.driver().findElement(element).click();
        browserWaits.waitForUrlChange(currentUrl);
        browserWaits.waitForAngular();
        browserWaits.waitForPageToBeLoadedCompletely();
    }

    public void navigateWithElement(By element, boolean getFocus, int maxTimeOut) {
        String currentUrl = browser.driver().getCurrentUrl();
        browserWaits.waitForElement(element);
        if(getFocus) {
            browser.driver().findElement(element).sendKeys("");
        }
        browser.driver().findElement(element).click();
        browserWaits.waitForUrlChange(currentUrl, maxTimeOut);
        browserWaits.waitForAngular();
        browserWaits.waitForPageToBeLoadedCompletely();
    }

    public void navigateWithSubmit() {
        String currentUrl = browser.driver().getCurrentUrl();
        browserForms.submitForm();
        browserWaits.waitForUrlChange(currentUrl);
        browserWaits.waitForAngular();
    }

    public void scrollToBottom() {

        int times = 0;
        long scroll = (Long) ((JavascriptExecutor)browser.driver()).executeScript(" return window.scrollY;");
        long previousScroll = 0L;
        do {
            previousScroll = scroll;
            ((JavascriptExecutor)browser.driver()).executeScript("window.scrollTo(0,5000);");
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
            }
            scroll = (Long) ((JavascriptExecutor)browser.driver()).executeScript(" return window.scrollY;");
            times++;
        }while (scroll != previousScroll && times < 5);

    }

    public void scrollToTop() {
        ((JavascriptExecutor)browser.driver()).executeScript("window.scrollTo(0,0);");
    }

    public void scrollToElement(By locator, boolean top) {
        if(top) {
            scrollToTop();
        }
        if(top && headerHeight == null) {
            WebElement element = browser.driver().findElements(HeaderComponent.HEADER_COMPONENT).get(0);
            headerHeight = element.getSize().getHeight();
        } else if(headerHeight == null) {
            headerHeight = 0;
        }
        JavascriptExecutor executor = (JavascriptExecutor) browser.driver();
        WebElement element = browser.driver().findElement(locator);
        executor.executeScript("window.scrollTo(0,"+ (element.getLocation().getY() - headerHeight.intValue()) +");");
    }

    public void refresh(){
        browser.driver().navigate().refresh();
        browserWaits.waitForPageToBeLoadedCompletely();
    }

    public void rotateDevice(final ScreenOrientation orientation) {
        if (browser.driver() instanceof AppiumDriver) {
            AppiumDriver appiumDriver = (AppiumDriver) browser.driver();
            appiumDriver.rotate(orientation);
        } else {
            throw new NotImplementedException("It is not a rotable device");
        }
    }

}