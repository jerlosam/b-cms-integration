package io.crazy88.beatrix.e2e.visual;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import internal.qaauto.picasso.java.client.core.dto.PicassoRectangle;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.ecomm.components.CashierComponent;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import io.crazy88.beatrix.e2e.player.components.AccountMenuComponent;
import io.crazy88.beatrix.e2e.player.components.HeaderComponent;
import lombok.SneakyThrows;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class VisualEcommSteps {

    private static final String TEST_PASSWORD = "Testing1";

    private static final String TEST_PLAYER = "ecomm_visualtesting@crazy88.io";

    private static final String TEST_FTD_PLAYER = "ecomm_ftd_visualtesting@crazy88.io";

    private static final String BLACKMAMBALITE = "BLACKMAMBALITE";

    private static final String DEPOSIT = "Deposit";

    private static final String WITHDRAW = "Withdraw";

    private static final By DEPOSIT_SELECTOR_BLACKMAMBALITE = By.cssSelector(".bx-account-menu-subcontent");

    private static final By DEPOSIT_SELECTOR_BLACKMAMBA = By.cssSelector("bx-overlay-container");

    private static final By QR_CODE = By.xpath("//bx-qr-component/div/div/div");

    private static final By WALLET_ADDRESS = By.xpath("//*[@id='wallet-address']");

    private static final By ACCOUNT_MENU_SUBCONTENT = By.cssSelector(".bx-account-menu-subcontent");

    private static final By PAYMENT_METHOD_SELECTOR = By.xpath("//bx-payment-method-selector/ul/li");

    public static final String IMAGES_TO_COMPARE = "IMAGES_TO_COMPARE";

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Autowired
    private HeaderComponent headerComponent;

    @Autowired
    private AccountMenuComponent accountMenuComponent;

    @Autowired
    private CashierComponent cashierComponent;

    @Autowired
    private VisualUtils visualUtils;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserNavigation browserNavigations;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private E2EProperties properties;

    @Autowired
    private Reporter reporter;

    @When("a player covers main flows of first time deposit")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversFirstTimeDepositFlow() {
        List<ImageValidation> images = new ArrayList<>();
        By depositSelector = getSelectorForFTDByBrandTemplate();
        String firstTimeDepositImageName = "firstTimeDeposit";

        loginSteps(TEST_FTD_PLAYER);

        headerComponent.navigateToDeposit();

        images = getImageValidationsOfCashier(images, firstTimeDepositImageName, depositSelector);

        return images;
    }

    private By getSelectorForFTDByBrandTemplate() {
        By depositSelector;
        if (properties.getBrandTemplate().equalsIgnoreCase(BLACKMAMBALITE)) {
            depositSelector = DEPOSIT_SELECTOR_BLACKMAMBALITE;
        } else {
            depositSelector = DEPOSIT_SELECTOR_BLACKMAMBA;
        }
        return depositSelector;
    }

    @When("a player covers main flows of deposit pages")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversDepositPagesFlow() {
        loginSteps(TEST_PLAYER);
        headerComponent.navigateToDeposit();
        browserWait.waitForElement(ACCOUNT_MENU_SUBCONTENT);

        return getImageValidations(DEPOSIT);
    }

    @When("a player covers main flows of withdraw pages")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversWithdrawPagesFlow() {
        loginSteps(TEST_PLAYER);
        headerComponent.navigateToDeposit();
        browserWait.waitForElement(ACCOUNT_MENU_SUBCONTENT);
        accountMenuComponent.navigateToWithdraw();
        browserWait.waitForElement(ACCOUNT_MENU_SUBCONTENT);

        return getImageValidations(WITHDRAW);
    }

    @SneakyThrows
    private List<ImageValidation> getImageValidations(String parentMethod) {
        List<ImageValidation> images = new ArrayList<>();
        String singleMethodImageName = "single" + parentMethod + "Page";
        String selectionPageImageName = parentMethod + "_SelectionPage";

        //Fixme: workaround in order to calculate properly the actual amount of payment methods received.
        Thread.sleep(2000);
        int paymentMethods = browser.driver().findElements(PAYMENT_METHOD_SELECTOR).size();

        if (paymentMethods == 0) {
            images = getImageValidationsOfCashier(images, singleMethodImageName, ACCOUNT_MENU_SUBCONTENT);
        } else {
            images.add(ImageValidation.builder()
                    .snapshot(visualUtils.captureImage(selectionPageImageName, ACCOUNT_MENU_SUBCONTENT))
                    .tolerance(0.1).build());

            for (int i = 1; i <= paymentMethods; i++) {
                //This is needed to avoid StaleElementReferenceException as the element is not attached to the DOM after
                // the last navigation.
                WebElement webElement = browser.driver().findElement(By.xpath("//bx-payment-method-selector/ul/li[" + i + "]"));
                String imageName = parentMethod + "_" + webElement.getText().replace(" ", "_");
                browserNavigations.navigateWithElement(By.id(webElement.getAttribute("id")), false);

                images = getImageValidationsOfCashier(images, imageName, ACCOUNT_MENU_SUBCONTENT);

                if (parentMethod.equalsIgnoreCase(DEPOSIT)) {
                    accountMenuComponent.navigateToDeposit();
                } else {
                    accountMenuComponent.navigateToWithdraw();
                }
                browserWait.waitForElement(PAYMENT_METHOD_SELECTOR);
            }
        }

        return images;
    }

    private List<ImageValidation> getImageValidationsOfCashier(List<ImageValidation> images, String imageName, By selector) {
        if (isBitcoinPage()) {
            PicassoRectangle rectangle = visualUtils.getRectangle(selector);
            PicassoRectangle qrCode = visualUtils.getRectangle(QR_CODE);
            PicassoRectangle walletAddress = visualUtils.getRectangle(WALLET_ADDRESS);

            images.add(ImageValidation.builder()
                    .snapshot(visualUtils.captureImage(imageName))
                    .region(rectangle)
                    .ignoredRegionList(Arrays.asList(qrCode, walletAddress)).tolerance(0.1).build());
        } else {
            images.add(ImageValidation.builder()
                    .snapshot(visualUtils.captureImage(imageName, selector)).tolerance(0.1).build());
        }

        return images;
    }

    private boolean isBitcoinPage() {
        return browser.driver().findElements(QR_CODE).size() > 0;
    }

    private void loginSteps(String player) {
        reporter.info("Player: " + player + " / " + TEST_PASSWORD + " (" + properties.getBrandDomain() + ")");
        playerNavigationActions.enterSite();
        stopBannerCarousel(true);
        playerNavigationActions.logIn(player, TEST_PASSWORD);
    }

    private void stopBannerCarousel(boolean firstSlide) {
        if (firstSlide) {
            browserWait.waitForElementPresent(By.cssSelector(".carousel-indicators li"));
            ((JavascriptExecutor) browser.driver()).executeScript("document.querySelector('.carousel-indicators li').click();");
        }
        ((JavascriptExecutor) browser.driver()).executeScript(
                "var interval=__zone_symbol__setInterval(function(){},10000); for(var i=0; i<=interval; i++){clearInterval(i);}");
    }
}
