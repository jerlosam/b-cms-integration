package io.crazy88.beatrix.e2e.browser;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@Component
public class BrowserForm {

    private static By DATEPICKER_APPLY_BUTTON = By.xpath(String.format("//BUTTON[text()='%s']", "Apply"));

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserWait browserWaits;

    @Autowired
    private BrowserNavigation browserNavigation;

    private By SUBMIT_BUTTON = By.cssSelector("button[type='submit']");

    public void fillTextField(By field, String text) {
        browserWaits.waitForElement(field);
        browser.fluent().input(field).clearField().sendKeys(text);
    }

    public void fillTextFieldAndEnter(By field, String text) {
        fillTextField(field,text);
        browser.fluent().input(field).sendKeys(Keys.RETURN);
    }

    public void fillTextFieldAndSpace(By field, String text) {
        fillTextField(field,text);
        browser.fluent().input(field).sendKeys(Keys.SPACE);
    }

    public void clearTextField(By field) {
        browser.fluent().input(field).clearField();
    }

    public void fillTextAreaField(By field, String text) {
        browserWaits.waitForElement(field);
        browser.fluent().textarea(field).clearField().sendKeys(text);
    }

    public void fillInDateField(By field, String date) {
        try {
            browser.fluent().element(field).click();
            browserWaits.waitForElement(DATEPICKER_APPLY_BUTTON);
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm a z");
            format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            this.fillTextField(field, format.format(new SimpleDateFormat("MM-dd-yyyy HH:mm").parse(date)));
            browserNavigation.scrollToElement(DATEPICKER_APPLY_BUTTON,true);
            browser.driver().findElement(DATEPICKER_APPLY_BUTTON).click();
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean exist(final By selector) {
        return !browser.driver().findElements(selector).isEmpty();
    }

    public void submitFormInContainer(By container) {
        browser.driver().findElement(container).findElement(SUBMIT_BUTTON).click();
    }

    public void submitForm(By button) {
        browser.driver().findElement(button).click();
        browserWaits.waitForAngular();
    }

    public void submitForm() {
        submitForm(SUBMIT_BUTTON);
    }

    public void selectValueOnSelector(By selector, String value) {
        selectValueOnSelector(selector, value, true);
    }

    public void selectValueOnSelector(By selector, String value, boolean strictValue) {
        browserWaits.waitForElement(selector);
        browser.driver().findElement(selector).findElement(By.className("custom-dropdown")).click();
        WebElement element = browser.driver().findElement(selector).findElement(By.className("custom-droplist"));

        if (strictValue) {
            selectValueEstrict(element, value);
        } else {
            selectValue(element, value);
        }
    }

    public void setValueOnSelect(By inputSelector, By optionSelector) {
        browserWaits.waitForElement(inputSelector);
        browser.fluent().element(inputSelector).click();
        browserWaits.waitForElement(optionSelector);
        browser.driver().findElement(optionSelector).click();
    }

    public String getValue(By element) {
        browserWaits.waitForElement(element);
        return browser.fluent().element(element).getText().toString();
    }

    public String getInputValue(By element) {
        return browser.fluent().input(element).getAttribute("value").toString();
    }

    public void clickOnElement(By container, By element) {
        browserWaits.waitForElement(container);
        browser.fluent().element(container).element(element).click();
    }

    public void clickOnElementByText(By container, By element, String text) {
        browserWaits.waitForElement(container);
        browser.fluent()
                .element(container)
                .elements(element).stream().filter(e -> e.getText().toString().equals(text))
                .findFirst()
                .ifPresent(FluentWebElement::click);
    }

    public void selectValue(WebElement list, final String value) {
        list.findElements(By.tagName("li")).stream().filter(option -> option.getText().contains(value)).findFirst()
                .ifPresent(WebElement::click);
    }

    public void selectValue(By select, String value) {
        browserWaits.waitForElement(select);
        browser.fluent().element(select).element(By.cssSelector("option[value='" + value + "']")).click();
    }

    public void selectValueEstrict(WebElement list, final String value) {
        browserWaits.waitForElement(By.xpath("//li[normalize-space(text())='" + value + "']"));
        list.findElement(By.xpath("//li[normalize-space(text())='" + value + "']")).click();
    }

    public void clearFieldByKeys(By field) {
        do {
            browser.driver().findElement(field).sendKeys(Keys.BACK_SPACE);
        } while (getInputValue(field).length() > 0);
    }

    public List<WebElement> getAllElements(By tag) {
        return browser.driver().findElements(tag);
    }

}
