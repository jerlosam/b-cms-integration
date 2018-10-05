package io.crazy88.beatrix.e2e.player.actions;

import com.google.common.collect.ImmutableMap;
import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.clients.SignupClient;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerSetupActions;
import io.crazy88.beatrix.e2e.customermanager.dto.Attributes;
import io.crazy88.beatrix.e2e.customermanager.dto.ManualAccountEntryByEndpoint;
import io.crazy88.beatrix.e2e.customermanager.dto.Security;
import io.crazy88.beatrix.e2e.player.clients.SiteConfigClient;
import io.crazy88.beatrix.e2e.player.form.signup.ProfileRegistrationFields;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import io.crazy88.beatrix.e2e.player.dto.SignupAddress;
import io.crazy88.beatrix.e2e.player.dto.SignupRequest;
import io.crazy88.beatrix.e2e.player.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;

@TestComponent
public class JoinTestActions {

    @Autowired
    private SignupClient signupClient;

    @Autowired
    private E2EProperties properties;

    @Autowired
    private SiteConfigClient siteConfigClient;

    @Autowired
    private CustomerManagerSetupActions customerManagerSetupActions;

    @Autowired
    private Reporter reporter;

    private static final String LANGUAGE = "en";

    public PlayerSignup signupRandomPlayerWithoutWebNavigation() {
        PlayerSignup playerSignup = PlayerSignup.generateRandomPlayerSignupData();
        signupClient.signup(getSignupRequest(playerSignup));
        return playerSignup;
    }

    public PlayerSignup signupRandomRefereePlayerWithoutWebNavigation(String email, String trackingCode) {
        PlayerSignup playerSignup = PlayerSignup.generateRandomRefereePlayerSignupData(email, trackingCode);
        signupClient.signup(getSignupRefereeRequest(playerSignup));
        return playerSignup;
    }

    public PlayerSignup signupPlayerWithEmail(String email) {
        PlayerSignup playerSignup = PlayerSignup.generateRandomPlayerSignupData();
        playerSignup.setEmail(email);
        signupClient.signup(getSignupRequest(playerSignup));
        return playerSignup;
    }

    public PlayerSignup signupRandomPlayerWithoutPinWithoutWebNavigation() {
        PlayerSignup playerSignup = PlayerSignup.generateRandomPlayerSignupData();
        if (pinIsRequired()) {
            reporter.warn("Pin code is required for this brand, player will created with pin code");
        } else {
            playerSignup.setPinCode(null);
        }
        signupClient.signup(getSignupRequest(playerSignup));
        return playerSignup;
    }

    private SignupRequest getSignupRequest(PlayerSignup playerSignup) {

        return SignupRequest.builder()
                .attributes(Attributes.builder()
                        .language(LANGUAGE)
                        .currency(properties.getCurrency())
                        .pin_code(playerSignup.getPinCode())
                        .build())
                .signupAddress(SignupAddress.builder()
                       .postalCode(playerSignup.getPostalCode())
                        .build()
                )
                .brandCode(properties.getBrandCode())
                .email(playerSignup.getEmail())
                .nickname(playerSignup.getNickName())
                .lastName(playerSignup.getLastName())
                .birthdate(playerSignup.getDateOfBirth())
                .firstName(playerSignup.getFirstName())
                .phone(playerSignup.getPhone())
                .security(Security.builder().password(playerSignup.getPassword()).build())
                .build();
    }

    private SignupRequest getSignupRefereeRequest(PlayerSignup playerSignup) {

        SignupRequest request = getSignupRequest(playerSignup);
        request = request.withReferral(ReferralSignup.builder()
                .referredBySource(playerSignup.getReferredBySource())
                .token(playerSignup.getToken())
                .type(playerSignup.getType()).build());
        request = request.withReferralEmail(ReferralEmailSignup.builder().referralEmail(playerSignup.getReferralEmail()).build());

        return request;
    }

    private boolean pinIsRequired() {
        final List<String> signupFormFields = siteConfigClient.getSignupConfig().getSignupFormFields();
        final List<String> signupFormOptionalFields = siteConfigClient.getSignupConfig().getSignupFormOptionalFields();
        return  signupFormFields.contains(ProfileRegistrationFields.FOURDIGITPINCODE.getFieldName())
                && !signupFormOptionalFields.contains(ProfileRegistrationFields.FOURDIGITPINCODE.getFieldName());
    }

    public void doManualAccountEntry(String currency, PlayerSignup playerSignup, Float amount) {
        ManualAccountEntryByEndpoint manualAccountEntry = ManualAccountEntryByEndpoint.builder()
                .amount(amount)
                .correlationRef("")
                .currency(currency)
                .category("BF")
                .strategy("CreditWithdrawable")
                .metadata(
                        ImmutableMap.builder()
                                .put("type", "agent")
                                .put("data", ImmutableMap.builder()
                                        .put("description", "QAA Test")
                                        .put("internalDescription", "").build()
                                ).build()
                ).build();


        customerManagerSetupActions.markAsTestAccountByEndpoint(playerSignup.getEmail());
        customerManagerSetupActions.getBalances(playerSignup.getEmail());
        customerManagerSetupActions.manualAccountEntry(playerSignup.getEmail(), manualAccountEntry);
    }
}
