package io.crazy88.beatrix.e2e;

import internal.katana.jbehave.JBehaveTestRunner;
import internal.katana.selenium.config.SeleniumSpringConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

// @ActiveProfiles({ "production", "defaultBrowser" })
// @ActiveProfiles({"test","defaultBrowser"})
@SpringBootTest(classes = { E2EConfiguration.class, SeleniumSpringConfig.class })
@EnableConfigurationProperties(E2EProperties.class)
public class TestRunnerIT extends JBehaveTestRunner {

}
