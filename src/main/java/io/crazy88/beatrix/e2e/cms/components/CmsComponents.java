package io.crazy88.beatrix.e2e.cms.components;

import java.io.File;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CmsComponents{

    @Autowired
    private BrowserNavigation browserNavigation;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserForm browserForm;

    private static final By USERNAME_FIELD = By.id("internalLogin-username");

    private static final By PASSWORD_FIELD = By.id("internalLogin-password");

    private static final By LOGIN_BUTTON = By.id("internalLogin-submit");

    private static final By PUBLISH_BUTTON = By.xpath("//button[contains(.,\"PUBLISH\")]");

    private static final By SAVE_MODAL_BUTTON = By.xpath("//button[contains(.,\"Save\")]");

    private static final By SEARCH_COMPONENTS_BUTTON = By.xpath("//button[contains(.,\"Search Components\")]");

    private static final By BRAND_SELECTOR = By.tagName("bx-brand-select");

    private static final By CREATE_NEW_CONTENT = By.cssSelector("[href=\"/content/new\"]");

    private static final By CREATE_NEW_MARKETING_CONTENT = By.cssSelector("a.bx-marketing_page-type");

    private static final By CREATE_NEW_CONTENT_PAGE = By.cssSelector(".bx-content-type-button.bx-content_page-type");

    private static final By CMS_INTERNAL_NAME = By.id("internal_name");

    private static final By CMS_TITLE = By.id("title");

    private static final By CMS_SLUG = By.id("slug");

    private static final By CMS_TAGS = By.id("tags");

    private static final By CMS_MODAL_SEARCH = By.id("searchBy");

    private static final By FILE = By.id("add_image_en");

    private static final By FILE_CARD_IMAGE = By.id("card_image_en");

    private static final By FILE_PAGE_IMAGE = By.id("page_image_en");

    private static final By CMS_PUBLISH = By.id("PUBLISH");

    private static final String DATE_NOW = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()).toString();

    private static final By CMS_BODY_EDITOR= By.id("tinymce");

    private static final By CONTENT_TAB = By.cssSelector("[href=\"/content\"]");

    private static final By MENU_TAB = By.cssSelector("[href=\"/menu\"]");

    private static final By CREATE_NEW_MENU = By.cssSelector("[href=\"/menu/new\"]");

    private static final By CARD_IMAGE_ALT = By.id("card_image_en.alt");

    private static final By PAGE_IMAGE_ALT = By.id("page_image_en.alt");

    private static String CMS_INTERNAL_NAME_INPUT_VALUE;

    private static final By ADD_MENU_ITEM = By.xpath("//button[contains(.,\"Add Menu Item\")]");

    public void navigateToCMS() {
      browserNavigation.navigateToUrlWithRedirection("https://contentmanager.makdev.intra-apps.com","/login?returnUrl=%2Fcontent%2Flist%2Fpublished");
      browserWait.waitForPageToBeLoadedCompletely();
    }

    public void selectBrand(String brand) {
      browserForm.selectValueOnSelector(BRAND_SELECTOR, brand);
    }

    public void loginToCMS(String player, String password) {
        browserWait.waitForElement(USERNAME_FIELD);
        browserForm.fillTextField(USERNAME_FIELD,player);
        browserForm.fillTextField(PASSWORD_FIELD,password);
        browserForm.submitForm(LOGIN_BUTTON);
    }

    public void createContent() {
      browser.driver().findElement(CONTENT_TAB).click();
      browserNavigation.navigateWithElement(CREATE_NEW_CONTENT);
    }

    public void createMarketingContent() {
      browser.driver().findElement(CREATE_NEW_MARKETING_CONTENT).click();
      browserForm.fillTextField(CMS_INTERNAL_NAME, "QA" + DATE_NOW);
      CMS_INTERNAL_NAME_INPUT_VALUE = browserForm.getInputValue(CMS_INTERNAL_NAME);
      browserForm.fillTextField(CMS_TAGS, "QA" + DATE_NOW);
      browserForm.fillTextField(CMS_TITLE, "QA" + DATE_NOW);
      File file = new File("cms_images/cms_test_image.jpg");
      //uploadImage(file);
      addDescriptionCMS();
      addComponentsCMS();
      browserWait.waitForElement(PUBLISH_BUTTON);
      browserForm.submitForm(PUBLISH_BUTTON);
    }

    public void uploadImage(File file) {
        String url = browser.driver().getCurrentUrl();
        browser.fluent().input(FILE).sendKeys(file.getAbsolutePath());
    }

    public void addACardImageCMS(File file) {
      String url = browser.driver().getCurrentUrl();
      browser.fluent().input(FILE_CARD_IMAGE).sendKeys(file.getAbsolutePath());
    }

    public void addAPageImageCMS(File file) {
      String url = browser.driver().getCurrentUrl();
      browser.fluent().input(FILE_PAGE_IMAGE).sendKeys(file.getAbsolutePath());
    }

    public void createContentPage() {
      browserWait.waitForElement(CREATE_NEW_CONTENT_PAGE);
      browser.driver().findElement(CREATE_NEW_CONTENT_PAGE).click();
      browserForm.fillTextField(CMS_INTERNAL_NAME, "QA" + DATE_NOW);
      CMS_INTERNAL_NAME_INPUT_VALUE = browserForm.getInputValue(CMS_INTERNAL_NAME);
      browserForm.fillTextField(CMS_TITLE, "QA" + DATE_NOW);
      //browserForm.fillTextField(CMS_SLUG, "QA" + DATE_NOW);
      File file_card_image = new File("cms_images/cms_test_image.jpg");
      addACardImageCMS(file_card_image);
      File file_page_image = new File("cms_images/cms_test_image_2.jpg");
      addAPageImageCMS(file_page_image);
      browserForm.fillTextField(CARD_IMAGE_ALT, "QA" + DATE_NOW);
      browserForm.fillTextField(PAGE_IMAGE_ALT, "QA" + DATE_NOW);
      addDescriptionCMS();
      browserWait.waitForElement(PUBLISH_BUTTON);
      browserForm.submitForm(PUBLISH_BUTTON);
      CMS_INTERNAL_NAME_INPUT_VALUE = browserForm.getInputValue(CMS_INTERNAL_NAME);
    }

    public void addDescriptionCMS() {
      browser.driver().switchTo().frame(0);
      browser.driver().findElement(CMS_BODY_EDITOR).sendKeys(DATE_NOW);
      browser.driver().switchTo().defaultContent();
    }

    public void addComponentsCMS() {
      browser.driver().findElement(SEARCH_COMPONENTS_BUTTON).click();
      browserWait.waitForElement(CMS_MODAL_SEARCH);
      browserForm.fillTextFieldAndEnter(CMS_MODAL_SEARCH,CMS_INTERNAL_NAME_INPUT_VALUE);
      By ADD_COMPONENT_ITEM_ADD = By.xpath("//*[contains(@class, \"bx-content-name\") and span = '" + CMS_INTERNAL_NAME_INPUT_VALUE + "']/following-sibling::td[3]/button");
      browserWait.waitForElement(ADD_COMPONENT_ITEM_ADD);
      browser.driver().findElement(ADD_COMPONENT_ITEM_ADD).click();
      browserWait.waitForElement(SAVE_MODAL_BUTTON);
      browser.driver().findElement(SAVE_MODAL_BUTTON).click();
    }

    public void createMenu() {
        browser.driver().findElement(MENU_TAB).click();
        browser.driver().findElement(CREATE_NEW_MENU).click();
        browserForm.fillTextField(CMS_INTERNAL_NAME, "QA" + DATE_NOW);
        browserForm.fillTextField(CMS_TAGS, "QA" + DATE_NOW);
        browserForm.fillTextField(CMS_TITLE, "QA" + DATE_NOW);
        addMenuItems();
        browserForm.submitForm(PUBLISH_BUTTON);
    }

    public void addMenuItems() {
        browser.driver().findElement(ADD_MENU_ITEM).click();
        By ADD_MENU_ITEM_ADD = By.xpath("//*[contains(@class, \"bx-content-name\") and span = \""+CMS_INTERNAL_NAME_INPUT_VALUE+"\"]/following-sibling::td[4]/button");
        browserWait.waitForElement(ADD_MENU_ITEM_ADD);
        browser.driver().findElement(ADD_MENU_ITEM_ADD).click();
        browser.driver().findElement(SAVE_MODAL_BUTTON).click();
    }

}
