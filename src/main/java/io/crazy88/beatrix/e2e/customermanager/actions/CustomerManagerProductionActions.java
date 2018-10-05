package io.crazy88.beatrix.e2e.customermanager.actions;

import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.customermanager.components.*;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerBalances;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerDetails;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerDetailsByAreas;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerUpdate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.apache.commons.lang3.RandomStringUtils.*;

@Profile("production")
@Component
public class CustomerManagerProductionActions extends CustomerManagerActions{

    protected PlayerUpdate generateRandomPlayerUpdateData() {

        String username = randomAlphabetic(8);

        return PlayerUpdate.builder()
                .firstName(String.format("FN%s", username))
                .lastName(String.format("LN%s", username))
                .username(username)
                .birthDate(getAdultBirthDate())
                .address(String.format("%s %s Street", randomNumeric(4), randomAlphabetic(6)))
                .state(String.format("state%s", randomAlphabetic(5)))
                .city(String.format("city%s", randomAlphabetic(6)))
                .postalCode(randomAlphanumeric(5))
                .phone(randomNumeric(10))
                .secondaryPhone(randomNumeric(10))
                .email(properties.getPlayer())
                .build();
    }

}
