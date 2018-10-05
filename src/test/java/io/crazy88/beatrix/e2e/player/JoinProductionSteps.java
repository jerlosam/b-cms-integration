package io.crazy88.beatrix.e2e.player;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.clients.dto.ProfileSearch;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.ToContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Profile;

@Profile("production")
@TestComponent
public class JoinProductionSteps extends JoinSteps{

    @Autowired
    private Reporter reporter;

    @Given("an existing player")
    @ToContext(value = PLAYER_SIGNUP_CONTEXT, retentionLevel = ToContext.RetentionLevel.SCENARIO)
    public PlayerSignup givenAnExistingPlayer() {
        return getProductionPlayer();
    }

    @Given("an existing player without pinCode")
    @ToContext(value = PLAYER_SIGNUP_CONTEXT, retentionLevel = ToContext.RetentionLevel.SCENARIO)
    public PlayerSignup givenAnExistingPlayerWithoutPinCode() {
        reporter.warn("Production player has pinCode, this step will be skipped");
        return getProductionPlayer();
    }
    
    @Given("two existing players with similar email (1 character different)")
    @ToContext(value = PLAYER_SIGNUP_CONTEXT, retentionLevel = ToContext.RetentionLevel.SCENARIO)
    public PlayerSignup givenTwoExistingPlayers() {
        return getProductionPlayer();
    }

    private PlayerSignup getProductionPlayer(){
        ProfileSearch profile = customerManagerSetupActions
                .getFirstProfileThatMatches(e2eProperties.getBrandCode(), e2eProperties.getPlayer())
                .orElseThrow(() -> new IllegalStateException(String.format("No profile found. Brand code: %s, Email: %s",
                        e2eProperties.getBrandCode(), e2eProperties.getPlayer())));

        return PlayerSignup.builder()
                .email(profile.getEmail())
                .password("Testing1")
                .nickName(profile.getNickname())
                .build();
    }
}
