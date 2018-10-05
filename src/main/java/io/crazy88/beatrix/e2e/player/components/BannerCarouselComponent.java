package io.crazy88.beatrix.e2e.player.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BannerCarouselComponent {

    @Autowired BrowserManager browser;

    @Autowired BrowserWait browserWaits;

    public static By BANNER_CAROUSEL_CONTAINER = By.cssSelector("bx-banner-carousel");

    private static By BANNER_CAROUSEL_INDICATORS = By.cssSelector("bx-banner-carousel .carousel-indicators li");

    private static By BANNER_SLIDES = By.cssSelector("bx-banner-carousel slide");

    public void navigateToSlide(int index) {
        browserWaits.waitForElement(BANNER_CAROUSEL_INDICATORS);
        browser.fluent().lis(BANNER_CAROUSEL_INDICATORS).get(index).click();
    }

    public void waitForSlide(int index) {
        browserWaits.waitUntil(10, (isSlideDisplayed) -> isSlideDisplayed(index));
    }

    public boolean isSlideDisplayed(int index) {
        return browser.fluent().elements(BANNER_SLIDES).get(index).getAttribute("class").toString().contains("active");
    }
}
