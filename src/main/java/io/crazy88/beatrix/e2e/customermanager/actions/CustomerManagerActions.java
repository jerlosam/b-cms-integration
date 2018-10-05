package io.crazy88.beatrix.e2e.customermanager.actions;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.AliceBackofficeProperties;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.EcommProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.bulkchangemanager.components.BackofficeAccountInfoComponent;
import io.crazy88.beatrix.e2e.common.internal.components.InternalLoginComponent;
import io.crazy88.beatrix.e2e.customermanager.components.ActivityComponent;
import io.crazy88.beatrix.e2e.customermanager.components.ManualAccountEntryComponent;
import io.crazy88.beatrix.e2e.customermanager.components.PaymentDetailsComponent;
import io.crazy88.beatrix.e2e.customermanager.components.PlayerBalancesComponent;
import io.crazy88.beatrix.e2e.customermanager.components.PlayerDetailsComponent;
import io.crazy88.beatrix.e2e.customermanager.components.RelatedAccountsComponent;
import io.crazy88.beatrix.e2e.customermanager.components.RewardsComponent;
import io.crazy88.beatrix.e2e.customermanager.components.SearchHeaderComponent;
import io.crazy88.beatrix.e2e.customermanager.components.SearchPlayerComponent;
import io.crazy88.beatrix.e2e.customermanager.components.TabsComponent;
import io.crazy88.beatrix.e2e.customermanager.components.TransactionsComponent;
import io.crazy88.beatrix.e2e.customermanager.components.UpdatePlayerDetailsComponent;
import io.crazy88.beatrix.e2e.customermanager.dto.ManualAccountEntry;
import io.crazy88.beatrix.e2e.customermanager.dto.PaymentDetails;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerBalances;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerDetails;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerDetailsByAreas;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerUpdate;
import io.crazy88.beatrix.e2e.customermanager.dto.RewardExclusions;
import io.crazy88.beatrix.e2e.ecomm.components.EcommLoginComponent;

@Profile({"local", "test"})
@Component
public class CustomerManagerActions {

    public static final String DATE_FORMAT = "MM-dd-y";

    @Autowired
    protected E2EProperties properties;

    @Autowired
    protected EcommProperties ecommProperties;

    @Autowired
    protected BrowserNavigation browserNavigation;

    @Autowired
    protected BrowserWait browserWaits;

    @Autowired
    protected SearchPlayerComponent searchPlayer;

    @Autowired
    protected PlayerDetailsComponent playerDetails;

    @Autowired
    protected PaymentDetailsComponent paymentDetails;

    @Autowired
    protected PlayerBalancesComponent playerBalances;

    @Autowired
    protected UpdatePlayerDetailsComponent updatePlayerDetails;

    @Autowired
    protected InternalLoginComponent internalLogin;

    @Autowired
    protected ManualAccountEntryComponent manualAccountEntry;

    @Autowired
    protected TransactionsComponent transactions;

    @Autowired
    protected RelatedAccountsComponent relatedAccount;

    @Autowired
    protected SearchHeaderComponent searchHeaderComponent;

    @Autowired
    protected TabsComponent tabs;

    @Autowired
    protected RewardsComponent rewardsComponent;

    @Autowired
    protected ActivityComponent activity;

    @Autowired
    private BackofficeAccountInfoComponent backofficeAccountInfoComponent;

    @Autowired
    protected AliceBackofficeProperties aliceBackofficeProperties;

    @Autowired
    protected EcommLoginComponent ecommLoginComponent;


    public void enterToCustomerManagerHomePage() {
        browserNavigation.navigateToUrlWithRedirection(properties.getCustomerManagerHomeUrl(), "login?returnUrl=%2Fadvanced-search");
        internalLogin.as(properties.getQaLdapUser(), properties.getQaLdapPassword());
    }

    public void searchByEmailOnCustomerManager(String email) {
        enterToCustomerManagerHomePage();
        searchPlayer.searchPlayerByEmail(email);
    }

    public void searchByEmailFromBulkChangeManagerInCustomerManager(String email) {
        browserNavigation.navigateToUrl(properties.getCustomerManagerHomeUrl() + "advanced-search");
        searchPlayer.searchPlayerByEmail(email);
    }

    public void enterToForbiddenPage() {
        browserNavigation.navigateToUrl(properties.getCustomerManagerHomeUrl() + "login?returnUrl=%2Fforbidden");
        internalLogin.as(properties.getQaLdapUser(), properties.getQaLdapPassword());
        browserWaits.waitForUrl(properties.getCustomerManagerHomeUrl() + "forbidden");
    }

    public void searchByEmail(String email) {
        searchPlayer.searchPlayerByEmail(email);
    }

    public void searchByPaymentIdOnCustomerManager(String paymentId) {

        //Workaround to skip ecomm basic auth popup
        browserNavigation.authenticateOnUrl(ecommProperties.getQaLdapUser(), ecommProperties.getQaLdapPassword(),
                ecommProperties.getCustomerManagerUrl());
        enterToCustomerManagerHomePage();
        searchHeaderComponent.searchByPaymentId(paymentId);
    }

    public boolean isPresentOnSearchResults(String playerEmail) {
        return searchPlayer.isPlayerDisplayedOnResultsTable(playerEmail, properties.getBrandCode());
    }

    public void navigateToPlayerFromSearchResults(String playerEmail) {
        searchPlayer.navigateToPlayerFromResultsTable(playerEmail, properties.getBrandCode());
    }

    public PlayerDetails getDisplayedPlayerDetailsFromCustomerManager() {
        return playerDetails.getPlayerDisplayed();
    }

    public PlayerDetailsByAreas getDisplayedPlayerDetailsByArea() {
        return playerDetails.getPlayerDisplayedByAreas();
    }

    public PaymentDetails getDisplayedPaymentDetailsFromCustomerManager() {
        return paymentDetails.getPaymentDisplayed();
    }

    public PlayerBalances getDisplayedPlayerBalancesFromCustomerManager() {
        browserNavigation.refresh();
        return playerBalances.getBalancesDisplayed();
    }

    public void navigateToRewardExclusions() {
        tabs.navigateToRewardsTab();
        rewardsComponent.navigateToRewardExclusions();
    }

    public void navigateToTargetLists() {
        tabs.navigateToRewardsTab();
        rewardsComponent.navigateToTargetLists();
    }

    public void navigateToRelatedAccounts() {
        tabs.navigateToRelatedAccountsTab();
    }

    public RewardExclusions getRewardExclusions() {
        return rewardsComponent.getRewardExlusions();
    }

    public void excludeAllRewards() {
        rewardsComponent.excludeAllRewards();
    }

    public PlayerDetails updatePlayerDetails() {
        playerDetails.navigateToUpdatePlayerDetails();
        PlayerUpdate updateData = generateRandomPlayerUpdateData();
        updatePlayerDetails.fillForm(updateData);
        String country = updatePlayerDetails.getCountryDisplayed();
        updatePlayerDetails.submitForm();

        String birthDate = updateData.getBirthDate();

        return PlayerDetails.builder()
                .name(getName(updateData))
                .address(updateData.getAddress())
                .address2(getAddress2(updateData, country))
                .postalCode(updateData.getPostalCode())
                .phone(updateData.getPhone())
                .email(updateData.getEmail())
                .birthDate(birthDate)
                .age(String.format("%s years", getYearsFromDate(birthDate)))
                .build();

    }

    public ManualAccountEntry performAManualAccountEntry(){
        playerDetails.navigateToManualAccountEntry();
        ManualAccountEntry manualAccountEntryData = generateManualAccountEntryData();
        manualAccountEntry.fillForm(manualAccountEntryData);
        manualAccountEntry.submitForm();
        return manualAccountEntryData;
    }

    private String getName(PlayerUpdate updateData) {
        return String.format("%s %s (%s)", updateData.getFirstName(), updateData.getLastName(), updateData.getUsername());
    }

    private String getAddress2(PlayerUpdate updateData, String country) {
        if (StringUtils.isEmpty(country)) {
            return String.format("%s %s", updateData.getCity(), updateData.getState());
        } else {
            return String.format("%s %s %s", updateData.getCity(), updateData.getState(), country);
        }
    }

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
                .email(String.format("%s@4null.com", username))
                .build();
    }

    protected ManualAccountEntry generateManualAccountEntryData() {
        return ManualAccountEntry.builder()
                .categories(ImmutableList.of("Financial", "Deposits", "Credit Card", "Visa/MasterCard"))
                .transactionRef("E2E".concat(randomAlphabetic(8)))
                .externalDescription("E2E Manual Adjustment External Desc")
                .internalDescription("E2E Manual Adjustment Internal Desc")
                .amount("0.01")
                .build();

    }

    protected String getAdultBirthDate() {
        LocalDate localDate = LocalDate.now().minus(19, ChronoUnit.YEARS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return localDate.format(formatter);
    }

    private long getYearsFromDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.until(LocalDateTime.now(), ChronoUnit.YEARS);
    }

    public String getDisplayedPlayerPPCsFromCustomerManager() throws ParseException {
        return playerDetails.getPPCsDisplayed();
    }

    public boolean isTransactionWithReferenceDisplayed(String transactionRef){
        tabs.navigateToTransactionsTab();
        return transactions.isTransactionsWithReferenceDisplayed(transactionRef);
    }

    public boolean isActivityWithActionDisplayed(String action) {
        tabs.navigateToActivityTab();
        return activity.isActivityWithActionDisplayed(action);
    }

    public boolean isRelatedAccountAffiliateRelationDisplayed(){
        tabs.navigateToRelatedAccountsTab();
        return relatedAccount.isReferredByPlayerDisplayed();
    }
    public Optional<String> getPlayerAccountNumber() {
        return playerDetails.getAccountNumber();
    }

    public void searchByAccountNumber(String accountNumber) {
        searchPlayer.quickSearchPlayer(accountNumber);
    }

    public boolean isRelatedAccountRafRelationDisplayed(String email) {
        return relatedAccount.isReferredByRafDisplayed(email);
    }

    public boolean isAffiliateRelationDisplayed(){
        return relatedAccount.isReferredByAffiliateDisplayed();
    }

    public boolean isCompanyAccountDisplayedInBackoffice() {
        relatedAccount.navigateToAliceProfile();
        return backofficeAccountInfoComponent.isCompanyAccountDisplayed();
    }

    public void loginBackoffice() {
        browserNavigation.navigateToUrl(aliceBackofficeProperties.getUrlLogin());
        browserNavigation.navigateToUrl(aliceBackofficeProperties.getUrlHome());
    }

    public String clickOnAddButtonOfFirstBonus() {
        return rewardsComponent.clickOnAddButtonOfFirstBonus();
    }

    public boolean bonusDisplayedPlayerLists(String expectedBonusCode) {
        return rewardsComponent.bonusDisplayedPlayerLists(expectedBonusCode);
    }

    public String clickOnRemoveButtonOfFirstBonus() {
        return rewardsComponent.clickOnRemoveButtonOfFirstBonus();
    }

    public boolean bonusDisplayedAvailableLists(String expectedBonusCode) {
        return rewardsComponent.bonusDisplayedAvailableLists(expectedBonusCode);
    }

    public boolean isSportsDetailsDisplayed() {
        return playerDetails.isSportsDetailsDisplayed();
    }
}
