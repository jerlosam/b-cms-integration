package io.crazy88.beatrix.e2e.customermanager.components;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class ActivityComponent {

    private static final By ACTIVITY_TABLE = By.xpath("//bx-activity-table//tbody");

    private static final By ACTION_COLUMNN = By.cssSelector(".action-col");

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;


    public boolean isActivityWithActionDisplayed(String actionTextExpected){
        browserWait.waitForElement(ACTIVITY_TABLE);
        browserWait.waitForAngular();
        List<WebElement> actions = actionsDisplayed(ACTION_COLUMNN);
        return actions.stream().anyMatch(action -> action.getText().contains(actionTextExpected));
    }

    private List<WebElement> actionsDisplayed(By locator){
        return browser.driver().findElement(ACTIVITY_TABLE).findElements(locator);
    }

}
