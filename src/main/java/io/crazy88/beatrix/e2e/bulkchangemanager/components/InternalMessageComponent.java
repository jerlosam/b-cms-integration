package io.crazy88.beatrix.e2e.bulkchangemanager.components;

import static org.openqa.selenium.By.id;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.BrowserForm;

@Component
public class InternalMessageComponent extends WizardStageComponentBase {

    private static final By NOTE_TOPIC = By.id("internalMessage-topic");

    private static final String TOPIC_OPTION_BY_NAME = "//input[@id='internalMessage-topic']/../ul/li[contains(text(), '%s')]";

    private static final By INTERNAL_NOTE = id("internalMessage-internalMessage");

    @Autowired
    protected BrowserForm browserForm;

    public void withTopicAndInternalNote(String topic, String internalNote) {
        browserForm.setValueOnSelect(NOTE_TOPIC, By.xpath(String.format(TOPIC_OPTION_BY_NAME, topic)));
        browserForm.fillTextAreaField(INTERNAL_NOTE, internalNote);
        clickSave();
    }
}
