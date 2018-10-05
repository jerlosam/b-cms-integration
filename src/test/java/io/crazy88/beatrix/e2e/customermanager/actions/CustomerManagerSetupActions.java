package io.crazy88.beatrix.e2e.customermanager.actions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.clients.BrandPropertiesCustomerManagerClient;
import io.crazy88.beatrix.e2e.clients.ProfileSearchClient;
import io.crazy88.beatrix.e2e.clients.dto.ProfileSearch;
import io.crazy88.beatrix.e2e.clients.dto.ProfileSearchResponse;
import io.crazy88.beatrix.e2e.customermanager.dto.BalanceDetail;
import io.crazy88.beatrix.e2e.customermanager.dto.ManualAccountEntryByEndpoint;

@TestComponent
public class CustomerManagerSetupActions {

    private static final String LOGIN_URL = "api/v1/session";

    private static final String SEARCH_URL = "api/v1/profiles/search";

    private static final String TAGS_URL = "services/profile/v1/profiles/%s";

    private static final String MANUAL_ACCOUNT_URL = "services/wallet-gateway/v1/profiles/%s/transactions/%s";

    private static final String BALANCES_URL = "services/wallet-gateway/v1/profiles/%s/balances";

    @Autowired
    E2EProperties e2eProperties;

    @Autowired
    Reporter reporter;

    @Autowired
    ProfileSearchClient profileSearchClient;

    @Autowired
    BrandPropertiesCustomerManagerClient brandPropsCmClient;

    private String getSessionToken(String internalUser, String internalPassword) {
        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String request = "{\"username\":\"" + internalUser + "\", \"password\":\"" + internalPassword + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<JsonNode> response = restTemplate.exchange(e2eProperties.getCustomerManagerHomeUrl() + LOGIN_URL,
                HttpMethod.POST, entity, JsonNode.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        return response.getBody().get("sessionId").asText();
    }

    private String getQASessionToken() {
        return getSessionToken(e2eProperties.getQaLdapUser(), e2eProperties.getQaLdapPassword());
    }

    private String getProfileId(String email) {
        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", getAuthorization());

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<JsonNode> response =
                restTemplate.exchange(e2eProperties.getCustomerManagerHomeUrl() + SEARCH_URL + "?query=" + email,
                        HttpMethod.GET, entity, JsonNode.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody().get(0).get("profileId").asText();
    }

    private String getAuthorization() {
        return "Bearer " + getQASessionToken();
    }

    private int getProfiles(String email) {
        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", getAuthorization());

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<JsonNode> response =
                restTemplate.exchange(e2eProperties.getCustomerManagerHomeUrl() + SEARCH_URL + "?query=" + email,
                        HttpMethod.GET, entity, JsonNode.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody().size();
    }

    public int searchEmail(String email) {
        return getProfiles(email);
    }

    private void updateTags(String profileId) {
        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", getAuthorization());

        String request = "{\"tags\": [\"TEST\"]}";
        HttpEntity<String> entity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<JsonNode> response =
                restTemplate.exchange(e2eProperties.getCustomerManagerHomeUrl() + String.format(TAGS_URL, profileId),
                        HttpMethod.PATCH, entity, JsonNode.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    public void markAsTestAccountByEndpoint(String email) {
        String profileId = getProfileId(email);
        reporter.debug("Trying to mark as TEST account: " + email);
        updateTags(profileId);
        reporter.debug(email + " marked as TEST");
    }

    public void manualAccountEntry(String email, ManualAccountEntryByEndpoint manualAccountEntry) {
        String profileId = getProfileId(email);
        reporter.debug("Trying to do a manual account adjustment to user: " + email);
        updateManualAccount(profileId, UUID.randomUUID().toString(), manualAccountEntry);
        reporter.debug(email + " manual account adjusted");
    }

    private void updateManualAccount(final String profileId, final String transactionId,
            final ManualAccountEntryByEndpoint request) {

        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + getQASessionToken());

        HttpEntity<ManualAccountEntryByEndpoint> entity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                e2eProperties.getCustomerManagerHomeUrl() + String.format(MANUAL_ACCOUNT_URL, profileId, transactionId),
                HttpMethod.PUT, entity, JsonNode.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    public BalanceDetail[] getBalances(final String email) {
        String profileId = getProfileId(email);
        reporter.debug("Trying to get balances for user: " + email);
        return getBalancesByProfileId(profileId);
    }

    private BalanceDetail[] getBalancesByProfileId(final String profileId) {
        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", getAuthorization());

        HttpEntity<ManualAccountEntryByEndpoint> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<BalanceDetail[]> response = restTemplate.exchange(
                e2eProperties.getCustomerManagerHomeUrl() + String.format(BALANCES_URL, profileId),
                HttpMethod.GET, entity, BalanceDetail[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    public Optional<ProfileSearch> getFirstProfileThatMatches(String brandCode, String query){
        ProfileSearchResponse profiles = profileSearchClient.searchProfile(getAuthorization(), query);
        return profiles.getItems().stream()
                       .filter(profile -> brandCode.equals(profile.getBrandCode()))
                       .findAny();
    }

    public boolean isSportsSupported() {
        return getBrandProperty("products").contains("SPORTS");
    }

    private Collection<String> getBrandProperty(String prefix) {
        return brandPropsCmClient.getProperties(getAuthorization(), e2eProperties.getBrandCode(), prefix)
                                 .getOrDefault(prefix, Collections.emptyList());

    }
}