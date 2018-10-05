package io.crazy88.beatrix.e2e.customermanager.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerUpdate;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePlayerDetailsComponent {

    private static final By FIRST_NAME = By.id("profileUpdateEnterprise-firstName");
    private static final By LAST_NAME = By.id("profileUpdateEnterprise-lastName");
    private static final By NICKNAME = By.id("profileUpdateEnterprise-nickname");
    private static final By DATE_OF_BIRTH = By.id("profileUpdateEnterprise-dateOfBirth");
    private static final By ADDRESS = By.id("profileUpdateEnterprise-address");
    private static final By STATE = By.id("profileUpdateEnterprise-subdivision");
    private static final By CITY = By.id("profileUpdateEnterprise-city");
    private static final By POSTAL_CODE = By.id("profileUpdateEnterprise-postalCode");
    private static final By PHONE_NUMBER = By.id("profileUpdateEnterprise-primaryPhone");
    private static final By SECONDARY_PHONE = By.id("profileUpdateEnterprise-secondaryPhone");
    private static final By EMAIL = By.id("profileUpdateEnterprise-email");
    private static final By COUNTRY = By.id("profileUpdateEnterprise-country");

    @Autowired
    private BrowserForm browserForms;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;

    public void fillForm(PlayerUpdate playerUpdate){
        browserForms.fillTextField(FIRST_NAME, playerUpdate.getFirstName());
        browserForms.fillTextField(LAST_NAME, playerUpdate.getLastName());
        browserForms.fillTextField(NICKNAME, playerUpdate.getUsername());
        browserForms.fillTextField(DATE_OF_BIRTH,playerUpdate.getBirthDate());
        browserForms.fillTextField(ADDRESS, playerUpdate.getAddress());
        browserForms.fillTextField(STATE, playerUpdate.getState());
        browserForms.fillTextField(CITY, playerUpdate.getCity());
        browserForms.fillTextField(POSTAL_CODE, playerUpdate.getPostalCode());
        browserForms.fillTextField(PHONE_NUMBER, playerUpdate.getPhone());
        browserForms.fillTextField(SECONDARY_PHONE, playerUpdate.getSecondaryPhone());
        if(!browserForms.getInputValue(EMAIL).equals(playerUpdate.getEmail())) {
            browserForms.fillTextField(EMAIL, playerUpdate.getEmail());
        }
    }

    public void submitForm(){
        String url = browser.driver().getCurrentUrl();
        browserForms.submitForm();
        browserWait.waitForUrlChange(url);
        browserWait.waitForPageToBeLoadedCompletely();
    }

    public String getCountryDisplayed(){
        return browserForms.getInputValue(COUNTRY);
    }
}
