package io.crazy88.beatrix.e2e.customermanager.components;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerDetails;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerDetailsByAreas;

@Component
public class PlayerDetailsComponent {

    private static final String PLAYER_DETAILS_TAG = "bx-profile-details-component";

    private static final By PLAYER_INFO_CONTAINER = By.tagName(PLAYER_DETAILS_TAG);
    private static final By PLAYER_NAME = By.cssSelector(PLAYER_DETAILS_TAG + " .header h2");
    private static final By PLAYER_ADDRESS = By.cssSelector(PLAYER_DETAILS_TAG + " .content .area:nth-child(1) .info span:nth-child(1)");
    private static final By PLAYER_ADDRESS_2 = By.cssSelector(PLAYER_DETAILS_TAG + " .content .area:nth-child(1) .info span:nth-child(2)");
    private static final By PLAYER_POSTAL_CODE = By.cssSelector(PLAYER_DETAILS_TAG + " .content .area:nth-child(1) .info span:nth-child(3)");
    private static final By PLAYER_PHONE = By.cssSelector(PLAYER_DETAILS_TAG + " .content .area:nth-child(2) .info span:nth-child(1)");
    private static final By PLAYER_EMAIL = By.cssSelector(PLAYER_DETAILS_TAG + " .content .area:nth-child(2) .info span:nth-child(2)");
    private static final By PLAYER_BIRTH_DATE = By.cssSelector(PLAYER_DETAILS_TAG + " .content .area:nth-child(3) .info span:nth-child(1)");
    private static final By PLAYER_AGE = By.cssSelector(PLAYER_DETAILS_TAG + " .content .area:nth-child(3) .info span:nth-child(2)");

    private static final By FIRST_AREA = By.cssSelector(PLAYER_DETAILS_TAG + " .content .area:nth-child(1) .info span");
    private static final By SECOND_AREA = By.cssSelector(PLAYER_DETAILS_TAG + " .content .area:nth-child(2) .info span");
    private static final By THIRD_AREA = By.cssSelector(PLAYER_DETAILS_TAG + " .content .area:nth-child(3) .info span");

    private static final By START_DATE_AREA = By.cssSelector(PLAYER_DETAILS_TAG + " .simple-table tbody tr:nth-child(1) td");
    private static final By PLAYER_PPCS = By.cssSelector(PLAYER_DETAILS_TAG + " bx-payment-profile-categories span");
    private static final By PLAYER_GROUP = By.cssSelector(PLAYER_DETAILS_TAG + " .simple-table tbody tr:nth-child(3) td");
    private static final By LINE_GROUP = By.cssSelector(PLAYER_DETAILS_TAG + " .simple-table tbody tr:nth-child(4) td");

    private static final By PLAYER_ACCOUNT_NUMBER = By.cssSelector(".account-info .account_number");

    private static final By UPDATE_PROFILE_LINK = By.linkText("Update Info");
    private static final By PLAYER_MESSAGE_LINK = By.linkText("Player Message");
    private static final By MANUAL_ACCOUNT_ENTRY_LINK = By.linkText("Manual Account Entry");

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserForm browserForm;

    @Autowired
    private BrowserNavigation browserNavigations;

    public PlayerDetailsByAreas getPlayerDisplayedByAreas(){
        browserWait.waitForElement(PLAYER_INFO_CONTAINER);

        List<WebElement> headerElements = browserForm.getAllElements(PLAYER_NAME);
        List<WebElement> firstAreaElements = browserForm.getAllElements(FIRST_AREA);
        List<WebElement> secondAreaElements = browserForm.getAllElements(SECOND_AREA);
        List<WebElement> thirdAreaElements = browserForm.getAllElements(THIRD_AREA);
        List<WebElement> startDateElements = browserForm.getAllElements(START_DATE_AREA);

        return PlayerDetailsByAreas.builder()
            .header(headerElements.stream().map(WebElement::getText).collect(Collectors.joining(",")))
            .firstArea(firstAreaElements.stream().map(WebElement::getText).collect(Collectors.joining(",")))
            .secondArea(secondAreaElements.stream().map(WebElement::getText).collect(Collectors.joining(",")))
            .thirdArea(thirdAreaElements.stream().map(WebElement::getText).collect(Collectors.joining(",")))
            .startDate(startDateElements.stream().map(WebElement::getText).collect(Collectors.joining(",")))
            .build();

    }

    public PlayerDetails getPlayerDisplayed(){
        browserWait.waitForElement(PLAYER_INFO_CONTAINER);

        return PlayerDetails.builder()
                .name(browserForm.getValue(PLAYER_NAME))
                .address(browserForm.getValue(PLAYER_ADDRESS))
                .address2(browserForm.getValue(PLAYER_ADDRESS_2))
                .postalCode(browserForm.getValue(PLAYER_POSTAL_CODE))
                .phone(browserForm.getValue(PLAYER_PHONE))
                .email(browserForm.getValue(PLAYER_EMAIL))
                .birthDate(browserForm.getValue(PLAYER_BIRTH_DATE))
                .age(browserForm.getValue(PLAYER_AGE))
                .build();
    }

    public void navigateToUpdatePlayerDetails(){
        browserNavigations.navigateWithElement(UPDATE_PROFILE_LINK);
    }

    public String getPPCsDisplayed() {
        browserWait.waitForElement(PLAYER_PPCS);
        return browserForm.getValue(PLAYER_PPCS);
    }

    public boolean isSportsDetailsDisplayed() {
        browserWait.waitForElement(PLAYER_INFO_CONTAINER);
        return browserForm.exist(PLAYER_GROUP) && browserForm.exist(LINE_GROUP);
    }

    public void navigateToPlayerMessage(){
        browserNavigations.navigateWithElement(PLAYER_MESSAGE_LINK);
    }

    public void navigateToManualAccountEntry() {
        browserNavigations.navigateWithElement(MANUAL_ACCOUNT_ENTRY_LINK);
    }

    public Optional<String> getAccountNumber() {
        Optional<String> accountNumber = Optional.empty();
        int times = 0;
        do {
            try {
                accountNumber = extractAccountNumber();
            } catch (TimeoutException e) {
                browserNavigations.refresh();
            }
            times++;
        } while (!accountNumber.isPresent() && times < 3);

        return accountNumber;
    }

    private Optional<String> extractAccountNumber() {
        return Optional.ofNullable(browserForm.getValue(PLAYER_ACCOUNT_NUMBER))
                .filter(accountNumber -> accountNumber.indexOf(" ") > 0)
                .map(accountNumber -> accountNumber.substring(0, accountNumber.indexOf(" ")))
                .filter(StringUtils::isNumeric);
    }
}
