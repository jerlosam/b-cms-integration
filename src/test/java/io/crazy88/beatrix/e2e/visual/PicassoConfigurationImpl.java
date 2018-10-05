package io.crazy88.beatrix.e2e.visual;

import internal.katana.selenium.BrowserManager;
import internal.katana.selenium.core.config.DefaultSeleniumConfig;
import internal.qaauto.picasso.java.client.core.PicassoConfiguration;
import io.crazy88.beatrix.e2e.E2EProperties;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PicassoConfigurationImpl implements PicassoConfiguration {

    @Autowired BrowserManager browser;

    @Autowired E2EProperties e2eProperties;

    @Autowired DefaultSeleniumConfig seleniumConfig;

    @Override public WebDriver getWebDriver() {
        return browser.driver();
    }

    @Override public String getDeployName() {
        return "beatrix";
    }

    @Override public String getVersion() {
        return "1.0";
    }

    @Override public String getPlatform() {
        return browser.browserDriver().browser().getPlatform();
    }

    @Override public String getBrowser() {
        if(browser.browserDriver().browser().getPublicName().equals("Chrome")) {
            int width = seleniumConfig.getScreenWidth();
            int height = seleniumConfig.getScreenHeight();
            if(width == 1920 && height == 1080) {
                return "Chrome";
            } else {
                return "Chrome" + width + "x" + height;
            }
        } else {
            return browser.browserDriver().browser().getPublicName();
        }
    }

    @Override public String getEnv() {
        if(e2eProperties.getEnvironment().toLowerCase().contains("muat")) {
            return "mdev";
        } else {
            return e2eProperties.getEnvironment();
        }
    }

    @Override public boolean isCompression() {
        return false;
    }
}