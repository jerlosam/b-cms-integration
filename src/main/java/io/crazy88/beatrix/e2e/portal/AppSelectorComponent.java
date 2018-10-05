package io.crazy88.beatrix.e2e.portal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class AppSelectorComponent {

    private static final By APP_SELECTOR = By.cssSelector("bx-app-selector");

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserForm browserForm;

    @Autowired
    private Reporter reporter;

    public boolean isPresent() {
        WebElement appSelector = findElement(APP_SELECTOR);
        reporter.info(String.format("App Selector: %s", appSelector.getText()));
        return true;
    }

    public String getText() {
        WebElement appSelector = findElement(APP_SELECTOR);
        String text = appSelector.getText();
        reporter.info(String.format("App Selector text: %s", text));
        return text;
    }

    public void navigateToFirstApp(){
        browserWait.waitForElement(APP_SELECTOR);
        getAppList().li().click();
    }

    public void navigateToApp(String appText){
        browserWait.waitForElement(APP_SELECTOR);
        WebElement element = getAppList().getWebElement();
        browserForm.selectValue(element, appText);
    }

    private FluentWebElement getAppList() {
        return browser.fluent().element(APP_SELECTOR).nav().ul();
    }

    private WebElement findElement(By element) {
        browserWait.waitForElement(element);
        return browser.driver().findElement(element);
    }

}
