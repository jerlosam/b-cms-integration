package io.crazy88.beatrix.e2e.customermanager.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.customermanager.dto.ManualAccountEntry;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManualAccountEntryComponent {

    private static final String SUBCATEGORY_SELECTOR = ".bx-category-selector-wrapper div:nth-child(%s) bx-dropdown";
    private static final By CATEGORY_SELECTOR = By.className("bx-category-selector-wrapper");
    private static final By TRANSACTION_REF = By.id("walletManualAdjustment-transRef");
    private static final By EXTERNAL_DESC = By.id("walletManualAdjustment-externalDesc");
    private static final By INTERNAL_DESC = By.id("walletManualAdjustment-internalDesc");
    private static final By AMOUNT = By.id("walletManualAdjustment-amount");

    @Autowired
    private BrowserForm browserForms;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;


    public void fillForm(ManualAccountEntry manualAccountEntry){
        selectCategories(manualAccountEntry.getCategories());
        browserForms.fillTextField(TRANSACTION_REF,manualAccountEntry.getTransactionRef());
        browserForms.fillTextAreaField(EXTERNAL_DESC,manualAccountEntry.getExternalDescription());
        browserForms.fillTextAreaField(INTERNAL_DESC,manualAccountEntry.getInternalDescription());
        browserForms.fillTextField(AMOUNT, manualAccountEntry.getAmount());
    }

    public void submitForm(){
        String url = browser.driver().getCurrentUrl();
        browserForms.submitForm();
        browserWait.waitForUrlChange(url);
        browserWait.waitForPageToBeLoadedCompletely();
    }

    private void selectCategories(List<String> categories){
        browserForms.selectValueOnSelector(CATEGORY_SELECTOR, categories.get(0));
        for(int i=1; i<categories.size(); i++) {
            By locator = getSubcategoryLocator(i);
            browserWait.waitForClickableElement(locator);
            browserWait.waitForAngular();
            browserForms.selectValueOnSelector(locator,categories.get(i));
        }
    }

    private By getSubcategoryLocator(Integer subcategoryLevel){
        return By.cssSelector(String.format(SUBCATEGORY_SELECTOR, subcategoryLevel + 1));
    }
}
