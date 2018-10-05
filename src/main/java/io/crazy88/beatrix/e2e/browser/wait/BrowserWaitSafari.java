package io.crazy88.beatrix.e2e.browser.wait;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("safari")
public class BrowserWaitSafari extends BrowserWait {

    // Safari web driver doesn't support this
    public void waitForAngular() {

    }
}
