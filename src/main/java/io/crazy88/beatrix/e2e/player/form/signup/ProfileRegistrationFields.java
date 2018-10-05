package io.crazy88.beatrix.e2e.player.form.signup;

import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static io.crazy88.beatrix.e2e.player.form.signup.ProfileRegistrationId.*;

public enum ProfileRegistrationFields {

    EMAIL(ProfileRegistrationId.EMAIL) {
        @Override
        public void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup) {

            browserForms.fillTextField(EMAIL_FIELD, playerSignup.getEmail());
        }
    },
    NICKNAME(ProfileRegistrationId.NICKNAME) {
        @Override
        public void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup) {

            browserForms.fillTextField(NICKNAME_FIELD, playerSignup.getNickName());
        }
    },
    PASSWORD(ProfileRegistrationId.PASSWORD) {
        @Override
        public void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup) {

            browserForms.fillTextField(PASSWORD_FIELD, playerSignup.getPassword());
        }
    },
    FIRSTNAME(ProfileRegistrationId.FIRSTNAME) {
        @Override
        public void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup) {

            browserForms.fillTextField(FIRSTNAME_FIELD, playerSignup.getFirstName());
        }
    },
    LASTNAME(ProfileRegistrationId.LASTNAME) {
        @Override
        public void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup) {

            browserForms.fillTextField(LASTNAME_FIELD, playerSignup.getLastName());
        }
    },
    DATEOFBIRTH(ProfileRegistrationId.DATEOFBIRTH) {
        @Override
        public void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup) {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("MM-dd-yyyy").withZone(ZoneId.systemDefault());
            browserForms.fillTextField(DATEOFBIRTH_FIELD,
                    formatter.format(playerSignup.getDateOfBirth()));
        }
    },
    CURRENCY(ProfileRegistrationId.CURRENCY) {
        @Override
        public void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup) {

            browserForms.selectValueOnSelector(CURRENCY_DROPDOWN, playerSignup.getCurrency());
        }
    },
    PHONE(ProfileRegistrationId.PHONE) {
        @Override
        public void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup) {

            browserForms.fillTextField(PHONE_FIELD, playerSignup.getPhone());
        }
    },
    FOURDIGITPINCODE(ProfileRegistrationId.FOUR_DIGIT_PIN_CODE) {
        @Override
        public void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup) {

            browserForms.fillTextField(FOUR_DIGIT_PIN_CODE_FIELD, playerSignup.getPinCode());
        }
    },
    TERMSCONDITIONCHECKBOX(ProfileRegistrationId.TERMS_CONDITIONS_CHECKBOX) {
        @Override
        public void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup) {
            browserForms.clickOnElement(TERMS_CONDITION_CONTAINER, TERMS_AND_CONDITIONS_CHECKBOX);
        }
    },
    POSTALCODE(ProfileRegistrationId.POSTAL_CODE) {
        @Override
        public void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup) {
            browserForms.fillTextField(POSTAL_CODE_FIELD, playerSignup.getPostalCode());

        }
    };

    private String fieldName;

    ProfileRegistrationFields(String fieldName) {

        this.fieldName = fieldName;
    }

    public String getFieldName() {

        return this.fieldName;
    }

    public abstract void fillFormField(BrowserForm browserForms, PlayerSignup playerSignup);

}
