package io.crazy88.beatrix.e2e.customermanager.components;

import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerBalances;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class PlayerBalancesComponent {
    private static final By TABLE_TOTAL_CASH = By.cssSelector("bx-account-balances-table table thead th:nth-child(3)");
    private static final By DOUGHNUT_TOTAL_BALANCE = By.className("total-balance");
    private static final By TABLE_BALANCES = By.cssSelector("bx-account-balances-table table tbody tr");
    private static final By BALANCE_NAME = By.cssSelector("td:nth-child(1)");
    private static final By BALANCE_AMOUNT = By.cssSelector("td:nth-child(3)");
	
	@Autowired
    private BrowserWait browserWaits;

    @Autowired
    private BrowserForm browserForms;

    public void waitForDoughnut() {
        browserWaits.waitForElement(TABLE_TOTAL_CASH);
    }

    @SneakyThrows
	public PlayerBalances getBalancesDisplayed() {
        waitForDoughnut();

        List<WebElement> balancesElements = browserForms.getAllElements(TABLE_BALANCES);
        Map<String, BigDecimal> balancesValue = new HashMap<>();
        for(WebElement balance:balancesElements) {
            balancesValue.put(balance.findElement(BALANCE_NAME).getText(),
                    bigDecimalFromString(balance.findElement(BALANCE_AMOUNT).getText()));

        }

        return PlayerBalances.builder()
                .totalCash(bigDecimalFromString(browserForms.getValue(TABLE_TOTAL_CASH)))
                .totalBalance(bigDecimalFromString(browserForms.getValue(DOUGHNUT_TOTAL_BALANCE).split(" ")[1]))
                .detailedBalances(balancesValue)
                .build();
    }
	
	private BigDecimal bigDecimalFromString(String amount) throws ParseException {
    	DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        df.setParseBigDecimal(true);
        return (BigDecimal) df.parseObject(amount);
    }
}
