package io.crazy88.beatrix.e2e.visual;

import internal.katana.selenium.BrowserManager;
import internal.katana.selenium.core.browser.BrowserVendor;
import internal.qaauto.picasso.java.client.core.dto.PicassoRectangle;
import io.appium.java_client.AppiumDriver;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.casino.actions.CasinoActions;
import io.crazy88.beatrix.e2e.casino.components.CasinoCardComponent;
import io.crazy88.beatrix.e2e.casino.components.GameWrapperComponent;
import io.crazy88.beatrix.e2e.player.NavigationSteps;
import io.crazy88.beatrix.e2e.player.actions.JoinActions;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import io.crazy88.beatrix.e2e.player.actions.ResetPasswordActions;
import io.crazy88.beatrix.e2e.player.clients.SiteConfigClient;
import io.crazy88.beatrix.e2e.player.components.AccountMenuComponent;
import io.crazy88.beatrix.e2e.player.components.ForgotPasswordComponent;
import io.crazy88.beatrix.e2e.player.components.HeaderComponent;
import io.crazy88.beatrix.e2e.player.components.LoginComponent;
import io.crazy88.beatrix.e2e.player.components.MessagesComponent;
import io.crazy88.beatrix.e2e.player.components.NotificationsComponent;
import io.crazy88.beatrix.e2e.player.components.ResetPasswordComponent;
import io.crazy88.beatrix.e2e.player.components.SearchGameComponent;
import io.crazy88.beatrix.e2e.player.components.SignupComponent;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import io.crazy88.beatrix.e2e.tools.RAFEnable;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;

@Component
public class VisualSteps {

    public static final String IMAGES_TO_COMPARE = "IMAGES_TO_COMPARE";

    private static final String TEST_PASSWORD = "Testing1";

    private static final String TEST_PLAYER = "visualtesting@crazy88.io";

    private static final String BLACKMAMBALITE = "BLACKMAMBALITE";

    public static final String BLACKMAMBA = "BLACKMAMBA";

    @Autowired
    private NavigationSteps navigationSteps;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Autowired
    private JoinActions joinActions;

    @Autowired
    private CasinoActions casinoActions;

    @Autowired
    private NotificationsComponent notificationsComponent;

    @Autowired
    private LoginComponent login;

    @Autowired
    private HeaderComponent headerComponent;

    @Autowired
    private ForgotPasswordComponent forgotPasswordComponent;

    @Autowired
    private VisualUtils visualUtils;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserNavigation browserNavigations;

    @Autowired
    private BrowserForm browserForms;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private ResetPasswordActions resetPasswordActions;

    @Autowired
    private E2EProperties properties;

    @Autowired
    private ResetPasswordComponent resetPasswordComponent;

    @Autowired
    private SearchGameComponent searchGameComponent;

    @Autowired
    private AccountMenuComponent accountMenu;

    @Autowired
    private MessagesComponent messagesComponent;

    @Autowired
    private SiteConfigClient siteConfigClient;

    @Autowired
    private BrowserWait browserWaits;

    private void stopBannerCarousel(boolean firstSlide) {
        if (firstSlide) {
            browserWait.waitForElementPresent(By.cssSelector(".carousel-indicators li"));
            ((JavascriptExecutor) browser.driver()).executeScript("document.querySelector('.carousel-indicators li').click();");
        }
        ((JavascriptExecutor) browser.driver()).executeScript(
                "var interval=__zone_symbol__setInterval(function(){},10000); for(var i=0; i<=interval; i++){clearInterval(i);}");
    }

    @When("a player covers main flows of home page")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversHomePage() {
        List<ImageValidation> images = new ArrayList<>();
        playerNavigationActions.enterSiteAsFirstTimeVisitor();

        if (browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI
                || browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.INTERNET_EXPLORER) {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureWholeImage("homeFirstTimeVisitor")).build());
        } else {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("homeTopFirstTimeVisitor")).build());

            browserNavigations.scrollToBottom();
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("homeBottomFirstTimeVisitor")).build());
        }
        playerNavigationActions.navigateToHome();

        stopBannerCarousel(true);
        //TO REVIEW if it does improve failure rate.
        browserWait.waitForNoAnimations();
        if (browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI
                || browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.INTERNET_EXPLORER) {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureWholeImage("homeVisitor")).build());
        } else {
            browserNavigations.scrollToTop();
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("homeTopVisitor")).build());

            browserNavigations.scrollToBottom();
            //TO REVIEW if it does improve failure rate.
            browserWait.waitForNoAnimations();
            images.add(
                    ImageValidation.builder().snapshot(visualUtils.captureImage("homeBottomVisitor")).tolerance(0.6).build());
        }

        playerNavigationActions.logIn(TEST_PLAYER, TEST_PASSWORD);

        //TO REVIEW if it does improve failure rate.
        browserWait.waitForNoAnimations();
        if (browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI
                || browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.INTERNET_EXPLORER) {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureWholeImage("homeLoggedIn")).build());
        } else {
            // Only one casino tab highlighted >> Refresh page
            browser.driver().navigate().refresh();
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("homeTopLoggedIn")).tolerance(0.2).build());

            browserNavigations.scrollToBottom();
            //TO REVIEW if it does improve failure rate.
            browserWait.waitForNoAnimations();
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("homeBottomLoggedIn")).build());
        }

        return images;
    }

    @When("a player covers main flows of signup")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversSignup() throws InterruptedException {
        List<ImageValidation> images = new ArrayList<>();

        playerNavigationActions.enterSite();

        navigationSteps.whenPlayerNavigatesToSignup();
        stopBannerCarousel(true);
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("signup", true)).ignoredRegionList(
                visualUtils.getIgnoredArea(SignupComponent.OVERLAY)).tolerance(0.4).build());

        // Workaround to fix visual testing. Instead to click in submit we "send" enter to display all errors.
        // We also remove error notification picture because not always is taken the notification picture
        // Last change is to take complete picture instead of overlay one, because overlay is bigger than screen
        browser.driver().findElement(By.id("registration-password")).sendKeys(Keys.ENTER);

        notificationsComponent.waitForNotificationsNotDisplayed();
        browser.driver().findElement(By.id("registration-email")).click();
        if (browser.browserDriver().browser().isMobile()) {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("signupError")).build());
        } else {
            images.add(ImageValidation.builder().snapshot(
                    visualUtils.captureImage("signupError", SignupComponent.OVERLAY, true)).build());
        }

        joinActions.fillSignUp(PlayerSignup.generatePicassoPlayerSignupData(), getSignupFields());

        //Wait until overlay is resized
        TimeUnit.SECONDS.sleep(1);

        if (browser.browserDriver().browser().isMobile()) {
            if (browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.ANDROID) {
                ((AppiumDriver) browser.driver()).hideKeyboard();
            }
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("signupCorrect")).build());

        } else {
            images.add(ImageValidation.builder().snapshot(
                    visualUtils.captureImage("signupCorrect", SignupComponent.OVERLAY, true)).tolerance(0.031).build());
        }

        return images;
    }

    @When("a player covers main flows of casino game page")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversCasinoGamePage() {
        List<ImageValidation> images = new ArrayList<>();

        playerNavigationActions.enterSite();

        playerNavigationActions.logIn(TEST_PLAYER, TEST_PASSWORD);

        casinoActions.navigateToCasinoGamePage(CasinoCardComponent.Game.A_NIGHT_WITH_CLEO);
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("casinoGame")).ignoredRegionList(
                visualUtils.getIgnoredArea(CasinoCardComponent.OVERLAY)).build());

        casinoActions.openFullDescription();
        if (!browser.browserDriver().browser().isDesktop()
                || browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI) {
            images.add(
                    ImageValidation.builder().snapshot(visualUtils.captureImage("casinoGameWithDescription")).ignoredRegionList(
                            visualUtils.getIgnoredArea(CasinoCardComponent.OVERLAY)).tolerance(1).build());
        } else {
            images.add(
                    ImageValidation.builder().snapshot(visualUtils.captureImage("casinoGameWithDescription")).ignoredRegionList(
                            visualUtils.getIgnoredArea(CasinoCardComponent.OVERLAY)).tolerance(0.04).build());
        }

        if (browser.browserDriver().browser().isDesktop()) {
            List<PicassoRectangle> ignoredRegionList = new ArrayList<>();

            casinoActions.playForPractice();

            ignoredRegionList.add(visualUtils.getRectangle(GameWrapperComponent.IFRAME));
            images.add(ImageValidation.builder().snapshot(
                    visualUtils.captureImage("casinoGameWrapperForPractice")).ignoredRegionList(ignoredRegionList).tolerance(
                    0.2).build());

            casinoActions.switchToRealMode();

            images.add(
                    ImageValidation.builder().snapshot(visualUtils.captureImage("casinoGameWrapperForReal")).ignoredRegionList(
                            ignoredRegionList).tolerance(0.1).build());
        }

        return images;
    }

    @When("a player covers main flows of login")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversLogin() {
        List<ImageValidation> images = new ArrayList<>();

        playerNavigationActions.enterSite();

        playerNavigationActions.navigateToLogin();
        stopBannerCarousel(true);
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("login", true)).ignoredRegionList(
                visualUtils.getIgnoredArea(LoginComponent.OVERLAY)).build());

        return images;
    }

    @When("a player covers main flows of account menu")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversAccountMenu() {
        List<ImageValidation> images = new ArrayList<>();

        playerNavigationActions.enterSite();

        playerNavigationActions.logIn(TEST_PLAYER, TEST_PASSWORD);

        //TO BE REVIEWED if it does improve failure rate due to background changing
        browserWait.waitForElement(By.cssSelector("div.content-slider.max-container"));

        headerComponent.navigateToAccountMenu();

        images.add(ImageValidation.builder()
                .snapshot(visualUtils.captureImage("accountMenu", AccountMenuComponent.OVERLAY)).build());

        return images;
    }

    @When("a player covers main flows of forgot password")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversForgotPassword() {

        List<ImageValidation> images = new ArrayList<>();

        playerNavigationActions.enterSite();

        playerNavigationActions.navigateToLogin();
        login.navigateToForgotPassword();
        stopBannerCarousel(true);
        if (browser.browserDriver().browser().isMobile()) {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("forgotPassword")).build());
        } else {

            images.add(ImageValidation.builder().snapshot(
                    visualUtils.captureImage("forgotPassword", ForgotPasswordComponent.OVERLAY, true)).tolerance(0.03).build());
        }

        browserForms.submitForm();
        browser.driver().findElement(By.id("forgotPassword-email")).click();
        if (browser.browserDriver().browser().isMobile()) {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("forgotPasswordError")).build());
        } else {
            images.add(ImageValidation.builder().snapshot(
                    visualUtils.captureImage("forgotPasswordError", ForgotPasswordComponent.OVERLAY, true)).build());
        }
        return images;
    }

    @RAFEnable
    @When("a player covers main flows of referAFriend page")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversReferAFriendPage() {
        List<ImageValidation> images = new ArrayList<>();

        playerNavigationActions.navigateToReferAFriend();

        if (browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI
                || browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.INTERNET_EXPLORER) {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureWholeImage("referAFriendVisitor")).build());
        } else {
            browserNavigations.scrollToTop();
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("referAFriendTopVisitor")).build());

            browser.driver().findElement(By.cssSelector("article.raf-intro > section.intro-section > a.custom-cta")).sendKeys(Keys.END);
            images.add(
                    ImageValidation.builder().snapshot(visualUtils.captureImage("referAFriendBottomVisitor")).tolerance(
                            0.6).build());
        }

        playerNavigationActions.logInThroughReferAFriend(TEST_PLAYER, TEST_PASSWORD);

        if (browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI
                || browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.INTERNET_EXPLORER) {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureWholeImage("referAFriendLoggedIn")).build());
        } else {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("referAFriendTopLoggedIn")).tolerance(
                    1).build());

            browser.driver().findElement(By.cssSelector("input.url-container")).sendKeys(Keys.END);
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("referAFriendBottomLoggedIn")).build());
        }

        return images;
    }

    @When("a player covers main flows of messages")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversMessagesFlow() {

        List<ImageValidation> images = new ArrayList<>();

        playerNavigationActions.enterSite();

        playerNavigationActions.logIn(TEST_PLAYER, TEST_PASSWORD);

        //TO BE REVIEWED if it does improve failure rate due to background changing
        browserWait.waitForElement(By.cssSelector("div.content-slider.max-container"));

        headerComponent.navigateToAccountMenu();

        accountMenu.navigateToMessages();

        if (browser.browserDriver().browser().isMobile()) {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("messageList"))
                    .build());
        } else {
            images.add(ImageValidation.builder()
                            .snapshot(visualUtils.captureImage("messageList", AccountMenuComponent.OVERLAY)).build());

            return images;

        }

        messagesComponent.openMessage();

        if (browser.browserDriver().browser().isMobile()) {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("messageDetail"))
                    .build());
        } else {


            images.add(ImageValidation.builder()
                    .snapshot(visualUtils.captureImage("messageDetail", AccountMenuComponent.OVERLAY)).build());

        }

        return images;
    }

    @When("a player covers main flows of reset password")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversResetPassword(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        List<ImageValidation> images = new ArrayList<>();

        playerNavigationActions.enterSite();
        String resetPasswordUrl = resetPasswordActions.getResetPasswordUrl(playerSignup.getEmail());
        browserNavigations.navigateToUrl(properties.getPlayerHomeUrl() + resetPasswordUrl);
        stopBannerCarousel(true);
        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage("resetPassword", ResetPasswordComponent.OVERLAY, true)).tolerance(0.022).build());

        browserForms.submitForm();
        browser.driver().findElement(By.id("password")).click();
        stopBannerCarousel(true);
        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage("resetPasswordError", ResetPasswordComponent.OVERLAY, true)).build());

        resetPasswordComponent.resetPassword(playerSignup.getPassword());
        notificationsComponent.waitForNotification();
        stopBannerCarousel(true);
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("resetPasswordSuccess")).build());

        return images;
    }

    @When("a player covers main flows of search game")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversSearchGame() {
        List<ImageValidation> images = new ArrayList<>();

        playerNavigationActions.enterSite();
        stopBannerCarousel(true);
        headerComponent.openSearch();

        searchGameComponent.searchFor("le");
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("searchResults")).build());

        searchGameComponent.searchFor("levisualtesting");
        browserWait.waitForElement(By.cssSelector(".not-found"));
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("searchNoResults")).build());

        return images;
    }

    @When("a player covers main flows of deposit")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversDepositFlow() throws InterruptedException {
        List<ImageValidation> images = new ArrayList<>();
        List<PicassoRectangle> regionList = new ArrayList<>();
        By depositSelector;

        playerNavigationActions.enterSite();
        stopBannerCarousel(true);

        playerNavigationActions.logIn(TEST_PLAYER, TEST_PASSWORD);

        headerComponent.navigateToDeposit();

        if (properties.getBrandTemplate().equalsIgnoreCase(BLACKMAMBALITE)) {
            depositSelector = By.cssSelector("bx-account-menu-cashier");
        } else {
            depositSelector = By.cssSelector("bx-overlay-container");
        }

        regionList.add(visualUtils.getRectangle(depositSelector));
        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage("deposit")).region(regionList.get(0)).tolerance(0.1).build());

        return images;
    }

    @When("player navigates to promotions page")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenPromotionsPageIsOpen(){
        List<ImageValidation> images = new ArrayList<>();

        playerNavigationActions.enterSite();

        playerNavigationActions.logIn(TEST_PLAYER, TEST_PASSWORD);

        String template = properties.getBrandTemplate();

        if (BLACKMAMBA.equals(template)) {
            playerNavigationActions.navigateToPromotions();
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("promotions")).build());
        }

        return images;
    }

    private List<String> getSignupFields() {
        return siteConfigClient.getSignupConfig().getSignupFormFields();
    }

}
