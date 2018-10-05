package io.crazy88.beatrix.e2e.player.actions;

import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.clients.ProfileClient;
import io.crazy88.beatrix.e2e.clients.TemporaryTokenClient;
import io.crazy88.beatrix.e2e.clients.dto.ProfileId;
import io.crazy88.beatrix.e2e.clients.dto.Token;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class ResetPasswordActions {

    @Autowired
    private E2EProperties properties;

    @Autowired
    private TemporaryTokenClient temporaryTokenClient;

    @Autowired
    private ProfileClient profileClient;

    public String getResetPasswordUrl(String email) {
        AssertionsHelper.retryUntilSuccessful(5, () -> assertThat(profileClient.search(properties.getBrandCode(), email).size() > 0).isTrue());
        ProfileId profileId = profileClient.search(properties.getBrandCode(), email).get(0);
        Token token = temporaryTokenClient.createToken(profileId);
        return String.format("reset-password/%s", token.getId().toString());
    }
}
