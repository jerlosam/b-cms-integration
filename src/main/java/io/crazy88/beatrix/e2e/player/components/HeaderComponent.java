package io.crazy88.beatrix.e2e.player.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeaderComponent {

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserValidation browserValidations;
    
    @Autowired
    private BrowserNavigation browserNavigations;

    @Autowired
    private BrowserWait browserWaits;

    public static By HEADER_COMPONENT = By.xpath("//div[contains(concat(' ', @class, ' '), 'navbar-wrapper')] | //*[@class='header-content']");

    private By ACCOUNT_BALANCE = By.cssSelector(".account-balance");

    private By DEPOSIT_BUTTON = By.cssSelector("bx-header-logged-actions.deposit-container a.custom-cta.deposit");

    private By DEPOSIT_LINK = By.cssSelector("bx-header-logged-actions.deposit-container div a.deposit");

    private By JOIN_BUTTON = By.cssSelector(".login-container a[routerlink='/join'].custom-cta");

    private By LOGIN_BUTTON = By.cssSelector(".login-container a[routerlink='/login'].custom-cta");

    private By MENU_BUTTON = By.className("menu-btn");

    public static By ACCOUNT_BUTTON = By.xpath("//bx-header-nav-menu-ch//following::button | //a[@class='user-menu-cta']");

    public boolean isAccountSectionDisplayed() {
        return browserValidations.isElementDisplayedInContainer(HEADER_COMPONENT, ACCOUNT_BALANCE);
    }
    
    public void navigateToDeposit() {
        if(browser.browserDriver().browser().isMobile()) {
            browserNavigations.navigateWithElement(DEPOSIT_LINK, false, 20);
        } else {
            browserNavigations.navigateWithElement(DEPOSIT_BUTTON, false, 20);
        }

    }

    public void navigateToJoin() {
        browserNavigations.navigateWithElement(JOIN_BUTTON);
        browserWaits.waitForElement(SignupComponent.OVERLAY_HEADER);
    }

    public void navigateToLogin(){
        browserNavigations.navigateWithElement(LOGIN_BUTTON);
    }
    
    public void navigateToAccountMenu() {
        browserNavigations.navigateWithElement(ACCOUNT_BUTTON);
        browserWaits.waitForElement(AccountMenuComponent.OVERLAY_HEADER);
    }

    public boolean isLoginButtonDisplayed() {
        return browserValidations.isElementDisplayedInContainer(HEADER_COMPONENT, LOGIN_BUTTON);
    }

    public void openSearch(){
        browser.fluent().element(SearchGameComponent.SEARCH_GAME_BUTTON).click();
    }

    public void navigateToNavMenu(){
        browser.driver().findElement(MENU_BUTTON).click();
        browserWaits.waitForElement(NavMenuComponent.LINK_PROMOTIONS);
    }

}
