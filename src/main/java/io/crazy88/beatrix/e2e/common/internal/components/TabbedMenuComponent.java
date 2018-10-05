package io.crazy88.beatrix.e2e.common.internal.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.BrowserForm;

@Component
public class TabbedMenuComponent {

    private static final By TABBED_MENU = By.cssSelector(".tabbed-menu");

    private static final By MENU_TOGGLE = By.cssSelector(".menu-toggle");

    @Autowired
    private BrowserForm browserForm;

    public void toggle(){
        browserForm.clickOnElement(TABBED_MENU, MENU_TOGGLE);
    }

}
