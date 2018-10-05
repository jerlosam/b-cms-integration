package io.crazy88.beatrix.e2e.player.components;

import io.crazy88.beatrix.e2e.browser.BrowserForm;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchGameComponent {

    @Autowired
    private BrowserForm browserForms;

    public static By SEARCH_GAME_BUTTON = By.cssSelector("bx-header .search-btn");

    public static By SEARCH_GAME_INPUT = By.cssSelector("bx-header .search-text-input");

    public void searchFor(String term) {
        browserForms.fillTextField(SEARCH_GAME_INPUT, term);
    }

}
