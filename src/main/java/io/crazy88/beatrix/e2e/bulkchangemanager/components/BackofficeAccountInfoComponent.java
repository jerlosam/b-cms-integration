package io.crazy88.beatrix.e2e.bulkchangemanager.components;

import static java.lang.String.format;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Set;

@Slf4j
@Component
public class BackofficeAccountInfoComponent {
	
	private static final By FORCE_PROFILE_UPDATE_BYPASS_BUTTON = By.id("forcePendingActionButton");
	
	private static final By LAST_MESSAGES_INTERNAL_CONTENT_ROWS = By.xpath("//div[contains(@id,'normalInternalContent')]");

	private static final By COMPANY_ACCOUNT_INFO = By.id("playerHeaderAccountInfoValue");
	
    @Autowired
    private BrowserManager browser;
    
    @Autowired
    private BrowserWait browserWait;
    
    public boolean isBypassButtonDisplayed() {
    	browserWait.waitForElement(FORCE_PROFILE_UPDATE_BYPASS_BUTTON);
    	return browser.driver().findElement(FORCE_PROFILE_UPDATE_BYPASS_BUTTON).isDisplayed();
    }

    public void clickOnBypassButton() {
    	browserWait.waitForElement(FORCE_PROFILE_UPDATE_BYPASS_BUTTON);
    	browser.fluent().button(FORCE_PROFILE_UPDATE_BYPASS_BUTTON).click();
    }

	public boolean isInternalMessageDisplayed(String internalMessage) {
		browserWait.waitForElement(LAST_MESSAGES_INTERNAL_CONTENT_ROWS);
		return browser.driver()
				.findElements(LAST_MESSAGES_INTERNAL_CONTENT_ROWS)
				.stream()
				.filter(e -> {
					log.info(format("Internal message: Expected [%s] Current [%s]", internalMessage, e.getText()));
					return e.getText().contains(internalMessage);
				})
				.findAny()
				.isPresent();
	}

	public boolean isCompanyAccountDisplayed() {
		Set<String> windowHandles = browser.driver().getWindowHandles();
		if(windowHandles.size() > 1) {
			browser.driver().switchTo().window(windowHandles.toArray()[1].toString());
		}
		browserWait.waitForElement(COMPANY_ACCOUNT_INFO);
		return browser.driver().findElement(COMPANY_ACCOUNT_INFO).isDisplayed();
	}
}
