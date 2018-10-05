package io.crazy88.beatrix.e2e.rewardmanager.actions;

import io.crazy88.beatrix.e2e.rewardmanager.dto.Template;
import org.springframework.stereotype.Component;

@Component
public class TemplateActions {

    public Template generateVisualTemplateData() {
        Template template = Template.builder()
                .templateName("TemplateForVisualTesting01234567890VisualTestingFramework")
                .casinoContribution(2)
                .build();
        return template;
    }
}
