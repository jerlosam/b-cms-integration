package io.crazy88.beatrix.e2e.rewardmanager.loyalty.components;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.BrowserForm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatePickerHelper {

    private static final By DATE_RANGE_CONTAINER = By.className("daterangepicker");

    private final BrowserForm browserForm;

    public void selectDate(final Instant date) {

        final LocalDateTime current = LocalDateTime.now();
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault());

        final long between = ChronoUnit.MONTHS.between(current, localDateTime);

        browserForm.clickOnElement(By.className("calendar-selector"), By.id("day-picker-input"));

        for (int i = 0; i < between; i++) {
            browserForm.clickOnElement(DATE_RANGE_CONTAINER, By.cssSelector("div.calendar th.next i"));
        }

        browserForm.clickOnElementByText(DATE_RANGE_CONTAINER, By.cssSelector("td.available"), String.valueOf(localDateTime.getDayOfMonth()));

        // It is always selecting same hour & minute (0:00)
        browserForm.selectValue(By.cssSelector("select.hourselect"), "10");
        browserForm.selectValue(By.cssSelector("select.minuteselect"), "10");

        browserForm.clickOnElement(DATE_RANGE_CONTAINER, By.cssSelector("button.applyBtn"));
    }
}
