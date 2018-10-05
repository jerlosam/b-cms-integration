package io.crazy88.beatrix.e2e.player.actions;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.player.components.NavMenuComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScreenOrientation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import internal.katana.selenium.core.browser.BrowserVendor;
import io.appium.java_client.AppiumDriver;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.player.components.AccountMenuComponent;
import io.crazy88.beatrix.e2e.player.components.FooterComponent;
import io.crazy88.beatrix.e2e.player.components.HeaderComponent;
import io.crazy88.beatrix.e2e.player.components.LoginComponent;
import io.crazy88.beatrix.e2e.player.components.NotificationsComponent;
import io.crazy88.beatrix.e2e.player.components.ReferAFriendComponent;

@Component
public class PlayerNavigationActions {

    @Autowired
    private E2EProperties properties;

    @Autowired
    private LoginComponent login;

    @Autowired
    private NotificationsComponent notifications;

    @Autowired
    private HeaderComponent header;

    @Autowired
    private FooterComponent footer;

    @Autowired
    private ReferAFriendComponent referAFriend;

    @Autowired
    private AccountMenuComponent accountMenu;

    @Autowired
    private BrowserNavigation browserNavigation;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private Reporter reporter;

    @Autowired
    private NavMenuComponent navMenuComponent;


    /**
     * Use this step only the very first time to enter the site, if you want to navigate to the home, use 'navigateToHome'
     */
    public void enterSiteAsFirstTimeVisitor(){
        if(!browser.browserDriver().browser().isDesktop()) {
            enterSiteWithCleanBrowser();
            if (browser.browserDriver().browser().isTablet() && ((AppiumDriver) browser.driver()).getOrientation() == ScreenOrientation.PORTRAIT) {
                ((AppiumDriver) browser.driver()).rotate(ScreenOrientation.LANDSCAPE);
            }
        } else {
           navigateToHome();
        }
    }

    private void enterSiteWithCleanBrowser() {
        navigateToHome();
        browser.browserDriver().cleanBrowser();
        ((JavascriptExecutor)browser.driver()).executeScript("window.location.reload(true);");
    }

    public void enterSite(){
        enterSiteAsFirstTimeVisitor();
        navigateToHome();
        //Something strange after cleaning cookies and local storage, a new refresh should be done in IE
        if(browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.INTERNET_EXPLORER
                || (browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.IOS && browser.browserDriver().browser().isMobile())) {
            browser.driver().navigate().refresh();
        }
    }

    public void navigateToHome(){
        browserNavigation.navigateToUrl(properties.getPlayerHomeUrl());
        browserWait.waitForPageToBeLoadedCompletely();

    }

    public void navigateToLogin() {
        if(browser.browserDriver().browser().isMobile()) {
            footer.navigateToLogin();
        } else {
            header.navigateToLogin();
        }
    }

    public void navigateToJoin() {
        if(browser.browserDriver().browser().isMobile()) {
            footer.navigateToJoin();
        } else {
            header.navigateToJoin();
        }
    }

    public void navigateToJoinThroughReferrer(String copyLink) {
        browserNavigation.navigateToUrlWithRedirectionContaining(copyLink, properties.getPlayerHomeUrl() + "join");
    }

    public void navigateToAccountMenu() {
        header.navigateToAccountMenu();
    }

    public void isPlayerInTheHomePage(){
        browserWait.waitForUrl(properties.getPlayerHomeUrl());
    }

    private void logInAs(String email, String password) {
        login.as(email, password);
        try {
            notifications.waitForNotification();
        } catch(Exception e) {

        }
        notifications.waitForNotificationsNotDisplayed();
    }

    public void logIn(String email, String password){
        navigateToLogin();
        logInAs(email, password);
    }

    public void logInThroughReferAFriend(String email, String password){
        referAFriend.navigateToLogin();
        logInAs(email, password);
    }

    public void logout(){
        header.navigateToAccountMenu();
        accountMenu.logout();
    }

    public boolean isLoggedIn(){
        return header.isAccountSectionDisplayed();
    }


    public void navigateToPreviousPage() {
        if(browser.browserDriver().browser().isMobile()) {
            browserNavigation.navigateWithElement(By.cssSelector(".back-btn"));
        } else {
            browser.driver().navigate().back();
        }
    }

    public void navigateToReferAFriend(){
        browserNavigation.navigateToUrl(properties.getPlayerReferAFriendUrl());
        browserWait.waitForElement(LoginComponent.OVERLAY_HEADER);
    }

    public void navigateToPromotions(){
        header.navigateToNavMenu();
        navMenuComponent.clickOnPromotions();
    }
}
