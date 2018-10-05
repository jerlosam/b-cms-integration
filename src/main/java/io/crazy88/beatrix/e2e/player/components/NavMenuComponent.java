package io.crazy88.beatrix.e2e.player.components;

import internal.katana.selenium.BrowserManager;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NavMenuComponent {

    @Autowired
    private BrowserManager browser;

    public static By LINK_PROMOTIONS = By.className("link-promotions");

    public void clickOnPromotions(){
        browser.driver().findElement(LINK_PROMOTIONS).click();
    }
}
