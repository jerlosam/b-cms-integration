package io.crazy88.beatrix.e2e.player.form.profileupdate;

import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.player.dto.PlayerSettings;
import org.apache.commons.lang.StringUtils;

import static io.crazy88.beatrix.e2e.player.form.profileupdate.ProfileUpdateId.*;

public enum ProfileUpdateFields {

    EMAIL(ProfileUpdateId.EMAIL) {
        @Override
        public void fillFormField(BrowserForm browserForm, PlayerSettings playerSettings) {
            browserForm.fillTextField(EMAIL_FIELD, playerSettings.getEmail());
        }

        @Override
        public void fillPlayerSetting(BrowserForm browserForm, PlayerSettings playerSettings) {
            playerSettings.setEmail(browserForm.getInputValue(EMAIL_FIELD));
        }

        @Override
        public boolean exist(BrowserForm browserForm) {
            return (browserForm.exist(EMAIL_FIELD));
        }

        @Override
        public boolean equals(PlayerSettings playerSettings, PlayerSettings currentPlayerSettings) {
            return StringUtils.equalsIgnoreCase(playerSettings.email, currentPlayerSettings.email);
        }
    },
    PHONE(ProfileUpdateId.PHONE) {
        @Override
        public void fillFormField(BrowserForm browserForm, PlayerSettings playerSettings) {
            browserForm.fillTextField(PHONE_FIELD, playerSettings.getPhone());
        }

        @Override
        public void fillPlayerSetting(BrowserForm browserForm, PlayerSettings playerSettings) {
            playerSettings.setPhone(browserForm.getInputValue(PHONE_FIELD));
        }

        @Override
        public boolean exist(BrowserForm browserForm) {
            return (browserForm.exist(PHONE_FIELD));
        }

        @Override
        public boolean equals(PlayerSettings playerSettings, PlayerSettings currentPlayerSettings) {
            return StringUtils.equalsIgnoreCase(playerSettings.phone, currentPlayerSettings.phone);
        }
    },
    NICKNAME(ProfileUpdateId.NICKNAME) {
        @Override
        public void fillFormField(BrowserForm browserForm, PlayerSettings playerSettings) {
            browserForm.fillTextField(NICKNAME_FIELD, playerSettings.getNickname());
        }

        @Override
        public void fillPlayerSetting(BrowserForm browserForm, PlayerSettings playerSettings) {
            playerSettings.setNickname(browserForm.getInputValue(NICKNAME_FIELD));
        }

        @Override
        public boolean exist(BrowserForm browserForm) {
            return (browserForm.exist(NICKNAME_FIELD));
        }

        @Override
        public boolean equals(PlayerSettings playerSettings, PlayerSettings currentPlayerSettings) {
            return StringUtils.equalsIgnoreCase(playerSettings.nickname, currentPlayerSettings.nickname);
        }
    },
    FIRSTNAME(ProfileUpdateId.FIRSTNAME) {
        @Override
        public void fillFormField(BrowserForm browserForm, PlayerSettings playerSettings) {
            browserForm.fillTextField(FIRSTNAME_FIELD, playerSettings.getFirstName());
        }

        @Override
        public void fillPlayerSetting(BrowserForm browserForm, PlayerSettings playerSettings) {
            playerSettings.setFirstName(browserForm.getInputValue(FIRSTNAME_FIELD));
        }

        @Override
        public boolean exist(BrowserForm browserForm) {
            return (browserForm.exist(FIRSTNAME_FIELD));
        }

        @Override
        public boolean equals(PlayerSettings playerSettings, PlayerSettings currentPlayerSettings) {
            return StringUtils.equalsIgnoreCase(playerSettings.firstName, currentPlayerSettings.firstName);
        }
    },
    LASTNAME(ProfileUpdateId.LASTNAME) {
        @Override
        public void fillFormField(BrowserForm browserForm, PlayerSettings playerSettings) {
            browserForm.fillTextField(LASTNAME_FIELD, playerSettings.getLastName());
        }

        @Override
        public void fillPlayerSetting(BrowserForm browserForm, PlayerSettings playerSettings) {
            playerSettings.setLastName(browserForm.getInputValue(LASTNAME_FIELD));
        }

        @Override
        public boolean exist(BrowserForm browserForm) {
            return (browserForm.exist(LASTNAME_FIELD));
        }

        @Override
        public boolean equals(PlayerSettings playerSettings, PlayerSettings currentPlayerSettings) {
            return StringUtils.equalsIgnoreCase(playerSettings.lastName, currentPlayerSettings.lastName);
        }
    },
    CITY(ProfileUpdateId.CITY) {
        @Override
        public void fillFormField(BrowserForm browserForm, PlayerSettings playerSettings) {
            browserForm.fillTextField(CITY_CODE_FIELD, playerSettings.getCity());
        }

        @Override
        public void fillPlayerSetting(BrowserForm browserForm, PlayerSettings playerSettings) {
            playerSettings.setCity(browserForm.getInputValue(CITY_CODE_FIELD));
        }

        @Override
        public boolean exist(BrowserForm browserForm) {
            return (browserForm.exist(CITY_CODE_FIELD));
        }

        @Override
        public boolean equals(PlayerSettings playerSettings, PlayerSettings currentPlayerSettings) {
            return StringUtils.equalsIgnoreCase(playerSettings.city, currentPlayerSettings.city);
        }
    },
    POSTALCODE(ProfileUpdateId.POSTAL_CODE) {
        @Override
        public void fillFormField(BrowserForm browserForm, PlayerSettings playerSettings) {
            browserForm.fillTextField(POSTAL_CODE_FIELD, playerSettings.getPostalCode());
        }

        @Override
        public void fillPlayerSetting(BrowserForm browserForm, PlayerSettings playerSettings) {
            playerSettings.setPostalCode(browserForm.getInputValue(POSTAL_CODE_FIELD));
        }

        @Override
        public boolean exist(BrowserForm browserForm) {
            return (browserForm.exist(POSTAL_CODE_FIELD));
        }

        @Override
        public boolean equals(PlayerSettings playerSettings, PlayerSettings currentPlayerSettings) {
            return StringUtils.equalsIgnoreCase(playerSettings.postalCode, currentPlayerSettings.postalCode);

        }
    },
    ADDRESS_LINE(ProfileUpdateId.ADDRESS_LINE) {
        @Override
        public void fillFormField(BrowserForm browserForm, PlayerSettings playerSettings) {
            browserForm.fillTextField(ADDRESS_LINE_FIELD, playerSettings.getAddressLine());
        }

        @Override
        public void fillPlayerSetting(BrowserForm browserForm, PlayerSettings playerSettings) {
            playerSettings.setAddressLine(browserForm.getInputValue(ADDRESS_LINE_FIELD));
        }

        @Override
        public boolean exist(BrowserForm browserForm) {
            return (browserForm.exist(ADDRESS_LINE_FIELD));
        }

        @Override
        public boolean equals(PlayerSettings playerSettings, PlayerSettings currentPlayerSettings) {
            return StringUtils.equalsIgnoreCase(playerSettings.addressLine, currentPlayerSettings.addressLine);
        }
    },
    LANGUAGES(ProfileUpdateId.LANGUAGES) {

        @Override
        public void fillFormField(BrowserForm browserForm, PlayerSettings playerSettings) {
            browserForm.selectValueOnSelector(LANGUAGES_DROPDOWN, playerSettings.language);
        }

        @Override
        public void fillPlayerSetting(BrowserForm browserForm, PlayerSettings playerSettings) {
            playerSettings.setLanguage(browserForm.getInputValue(LANGUAGES_FIELD));
        }

        @Override
        public boolean exist(BrowserForm browserForm) {
            return (browserForm.exist(LANGUAGES_FIELD));
        }

        @Override
        public boolean equals(PlayerSettings playerSettings, PlayerSettings currentPlayerSettings) {
            return StringUtils.equalsIgnoreCase(playerSettings.language, currentPlayerSettings.language);
        }
    },
    TIMEZONES(ProfileUpdateId.TIMEZONES) {

        @Override
        public void fillFormField(BrowserForm browserForm, PlayerSettings playerSettings) {
            browserForm.selectValueOnSelector(TIMEZONES_DROPDOWN, playerSettings.timezone);
        }

        @Override
        public void fillPlayerSetting(BrowserForm browserForm, PlayerSettings playerSettings) {
            playerSettings.setTimezone(browserForm.getInputValue(TIMEZONES_FIELD));
        }

        @Override
        public boolean exist(BrowserForm browserForm) {
            return (browserForm.exist(TIMEZONES_FIELD));
        }

        @Override
        public boolean equals(PlayerSettings playerSettings, PlayerSettings currentPlayerSettings) {
            return StringUtils.equalsIgnoreCase(playerSettings.timezone, currentPlayerSettings.timezone);
        }
    };

    private String fieldName;

    ProfileUpdateFields(String fieldName) {

        this.fieldName = fieldName;
    }

    public String getFieldName() {

        return this.fieldName;
    }

    public abstract void fillFormField(BrowserForm browserForm, PlayerSettings playerSettings);

    public abstract void fillPlayerSetting(BrowserForm browserForm, PlayerSettings playerSettings);

    public abstract boolean exist(BrowserForm browserForm);

    public abstract boolean equals(PlayerSettings playerSettings, PlayerSettings currentPlayerSettings);

}
