package io.crazy88.beatrix.e2e.browser.wait;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import internal.qaauto.picasso.java.client.core.exceptions.PicassoException;
import internal.qaauto.picasso.java.client.jbehave.PicassoClientUtils;
import io.crazy88.beatrix.e2e.visual.FastImageComparison;
import junit.framework.ComparisonFailure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
@Profile({ "defaultBrowser", "android", "internet_explorer", "edge" })
public class BrowserWait {

    private static final int MAX_SECONDS_TO_WAIT = 10;

    @Autowired
    protected BrowserManager browser;

    @Autowired
    private Reporter reporter;

    public void waitForAngular() {
        browser.angular().waitForAngularRequestsToFinish();
    }

    public WebElement waitForElement(By element) {
        return (new WebDriverWait(browser.driver(), MAX_SECONDS_TO_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public void waitForElementNotDisplayed(By element) {
        (new WebDriverWait(browser.driver(), MAX_SECONDS_TO_WAIT))
                .until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    public WebElement waitForClickableElement(By element) {
        return (new WebDriverWait(browser.driver(), MAX_SECONDS_TO_WAIT))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForClickableElement(WebElement element) {
        return (new WebDriverWait(browser.driver(), MAX_SECONDS_TO_WAIT))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementPresent(By element) {
        waitUntil((Function) isPresent -> browser.driver().findElement(element));
    }

    public void waitForUrlChange(String oldUrl) {
        waitUntil((Function) (hasUrlChanged) -> !(oldUrl.equals(browser.driver().getCurrentUrl())));
    }

    public void waitForUrlChange(String oldUrl, int maxTimeOut) {
        waitUntil(maxTimeOut, (Function) (hasUrlChanged) -> !(oldUrl.equals(browser.driver().getCurrentUrl())));
    }

    public void waitForPageToBeLoadedCompletely() {
        waitUntil((Function) (isElementCompleted) -> ((JavascriptExecutor) browser.driver()).executeScript(
                "return document.readyState").equals("complete"));
    }

    public void waitForUrl(String newUrl) {
        waitUntil((Function) isNewUrlLoad -> newUrl.equals(browser.driver().getCurrentUrl()));
    }

    public void waitForUrlContaining(String textOnURL) {
        waitUntil((Function) isNewUrlContainingText -> browser.driver().getCurrentUrl().contains(textOnURL));
    }

    public File waitForNoAnimations() {
        return waitForNoAnimations(2, 200);
    }

    public File waitForNoAnimations(final int timeout, final long checkStep) {
        waitForPageToBeLoadedCompletely();
        List<File> screenshots = new ArrayList<>();
        try {
            waitUntil(timeout, isAnimationFinished -> {
                try {
                    File screenshot;
                    if (screenshots.isEmpty()) {
                        screenshot = PicassoClientUtils.takeScreenshot(browser.driver());
                        screenshots.add(screenshot);
                    } else {
                        screenshot = screenshots.stream().reduce((first, second) -> second).get();
                    }
                    TimeUnit.MILLISECONDS.sleep(checkStep);
                    File secondScreenshot = PicassoClientUtils.takeScreenshot(browser.driver());
                    screenshots.add(secondScreenshot);
                    return FastImageComparison.isEqual(screenshot, secondScreenshot);
                } catch (PicassoException e) {
                    reporter.debug("PicassoException:" + e.getMessage());
                    return true;
                } catch (InterruptedException e) {
                    reporter.debug("InterruptedException:" + e.getMessage());
                    return true;
                }
            });
        } catch (TimeoutException exception) {
        }
        return screenshots.stream().reduce((first, second) -> second).get();
    }

    public void waitUntil(Function isTrue) {
        new FluentWait<>(browser.driver())
                .withTimeout(MAX_SECONDS_TO_WAIT, SECONDS)
                .pollingEvery(1, SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(ComparisonFailure.class)
                .ignoring(WebDriverException.class)
                .until(isTrue);
    }

    public void waitUntil(int timeOutSeconds, Function isTrue) {
        new FluentWait<>(browser.driver())
                .withTimeout(timeOutSeconds, SECONDS)
                .pollingEvery(1, SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(ComparisonFailure.class)
                .ignoring(WebDriverException.class)
                .until(isTrue);
    }
}
