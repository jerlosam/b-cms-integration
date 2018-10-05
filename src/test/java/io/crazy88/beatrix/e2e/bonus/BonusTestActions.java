package io.crazy88.beatrix.e2e.bonus;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import internal.katana.core.reporter.Reporter;
import lombok.extern.slf4j.Slf4j;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.crazy88.beatrix.boot.service.controller.Slice;
import io.crazy88.beatrix.boot.service.exception.NotFoundException;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.bonus.dto.Amount;
import io.crazy88.beatrix.e2e.bonus.dto.Availability;
import io.crazy88.beatrix.e2e.bonus.dto.Bonus;
import io.crazy88.beatrix.e2e.bonus.dto.CreateRewardRequest;
import io.crazy88.beatrix.e2e.bonus.dto.Description;
import io.crazy88.beatrix.e2e.bonus.dto.Expiration;
import io.crazy88.beatrix.e2e.bonus.dto.ExternalDescription;
import io.crazy88.beatrix.e2e.bonus.dto.Funds;
import io.crazy88.beatrix.e2e.bonus.dto.Outcome;
import io.crazy88.beatrix.e2e.bonus.dto.Rollover;
import io.crazy88.beatrix.e2e.bonus.dto.RolloverFactor;
import io.crazy88.beatrix.e2e.bonus.dto.Trigger;
import io.crazy88.beatrix.e2e.bonus.dto.UseRestriction;
import io.crazy88.beatrix.e2e.bonus.dto.types.PriorityType;
import io.crazy88.beatrix.e2e.bonus.dto.types.RolloverBase;
import io.crazy88.beatrix.e2e.clients.BrandPropertiesClient;
import io.crazy88.beatrix.e2e.clients.ProfileClient;
import io.crazy88.beatrix.e2e.clients.ReferenceDataClient;
import io.crazy88.beatrix.e2e.clients.RewardAvailabilityClient;
import io.crazy88.beatrix.e2e.clients.RewardManagementClient;
import io.crazy88.beatrix.e2e.clients.WalletConstraintsClient;
import io.crazy88.beatrix.e2e.clients.dto.AvailableRewards;
import io.crazy88.beatrix.e2e.clients.dto.BrandRegion;
import io.crazy88.beatrix.e2e.clients.dto.ProfileId;
import io.crazy88.beatrix.e2e.clients.dto.Template;

@TestComponent
@Slf4j
public class BonusTestActions {

    @Autowired
    private E2EProperties properties;

    @Autowired
    private RewardManagementClient rewardManagementClient;

    @Autowired
    private WalletConstraintsClient walletConstraintsClient;

    @Autowired
    private ReferenceDataClient referenceDataClient;

    @Autowired
    private BrandPropertiesClient brandPropertiesClient;

    @Autowired
    private ProfileClient profileClient;

    @Autowired
    private RewardAvailabilityClient rewardAvailabilityClient;

    public Bonus createUpfrontBonus(){
        return null;
    }

    public Bonus createReward(){
        CreateRewardRequest request = generateRewardRequest();
        UUID rewardId = rewardManagementClient.createReward(request);

        Map<String, BigDecimal> amounts = new HashMap<>();
        String currency = request.getOutcome().getFunds().getAmounts().keySet().stream().findFirst().get();
        BigDecimal value = request.getOutcome().getFunds().getAmounts().get(currency).getValue().setScale(2);
        amounts.put(currency,value);

        BigDecimal factorValue = request.getOutcome().getFunds().getRollover().getFactors().stream().findFirst().get().getValue();

        String firstLanguageCode = request.getDescription().getExternal().keySet().stream().findFirst().get();

        return Bonus.builder()
                .bonusId(rewardId)
                .internalName(request.getDescription().getInternal())
                .externalName(request.getDescription().getExternal().get(firstLanguageCode).getName())
                .description(request.getDescription().getExternal().get(firstLanguageCode).getDescription())
                .startDate(request.getAvailability().getStartsAt().toString())
                .endDate(request.getAvailability().getEndsAt().toString())
                .amounts(amounts)
                .lockAmount(request.getOutcome().getFunds().getLockAmount().getValue())
                .rolloverBase(0)
                .factor(factorValue)
                .build();
    }

    public CreateRewardRequest generateRewardRequest(){
        UUID regionId = getRegionIdPerName(properties.getBrandCode(), properties.getRegionName());
        List<String> languagesCode = getLanguagesCodePerBrandAndRegion(properties.getBrandCode(), regionId);
        return CreateRewardRequest.builder()
                .brandCode(properties.getBrandCode())
                .availability(generateAvailability(regionId))
                .description(generateDescription(languagesCode))
                .outcome(generateOutcome())
                .build();
    }

    public boolean isBonusAvailableForPlayer(Bonus bonus, String brandCode, String email){
        ProfileId profileId = profileClient.search(brandCode, email.toLowerCase()).get(0);
//        AvailableRewards availableRewards = rewardAvailabilityClient.getAvailableRewards(profileId.getProfileId().toString());
//        Optional<AvailableReward> availableReward = availableRewards.getAvailableRewards().stream()
//                .filter(reward -> reward.getDescription().getInternal().equals(bonus.getInternalName()))
//                .findAny();
//
//        return availableReward.isPresent();
        return true;

    }

    public AvailableRewards getBonusAvailableForPlayer(String brandCode, String email) {
        ProfileId profileId = profileClient.search(brandCode, email.toLowerCase()).get(0);
        return rewardAvailabilityClient.getAvailableRewards(profileId.getProfileId().toString());
    }

    public String getCurrencyFromBrandProperties(String brandCode){
        log.info("getCurrencyFromBrandProperties: {}", brandCode);
        List<BrandRegion> regions = referenceDataClient.readAllByBrand(brandCode).getItems();
        log.info("Regions: {}", Arrays.toString(regions.toArray()));
        BrandRegion region = getFirstRegion(regions);
        log.info("FirstRegion: {}", region);
        List<String> currencies = brandPropertiesClient.getPropertiesPerRegion(brandCode,getRegionIdPerName(brandCode,region.getName()).toString()).get("currencies.default");
        log.info("Currencies: {}" ,currencies);
        return currencies.get(0);
    }

    private BrandRegion getFirstRegion(List<BrandRegion> regions) {
        BrandRegion region;
        if (regions.size() == 1) {
            region = regions.get(0);
        } else {
            regions.sort(Comparator.comparing(BrandRegion::getName));
            region = regions.get(0);
        }
        return region;
    }

    private Rollover generateRollover() {
        Slice<Template> templates = walletConstraintsClient.getTemplates(properties.getBrandCode());
        Optional<Template> template = templates.getItems().stream().findFirst();
        if (!template.isPresent()) throw new NotFoundException();

        RolloverFactor factor = RolloverFactor.builder()
                .productCode("CASINO")
                .value(BigDecimal.valueOf(2))
                .build();
        return Rollover.builder()
                .base(RolloverBase.DEPOSIT)
                .constraintsTemplateId(template.get().getId())
                .factors(Sets.newSet(factor))
                .build();
    }

    private Outcome generateOutcome(){
        Amount amount = Amount.builder().type("FIXED").value(BigDecimal.valueOf(1000)).build();
        Map<String,Amount> amounts = new HashMap<>();
        amounts.put(properties.getCurrency(), amount);
        Amount lockAmount = Amount.builder().type("PERCENTAGE").value(BigDecimal.valueOf(0.5)).build();
        Funds funds = Funds.builder()
                .amounts(amounts)
                .expiration(Expiration.builder().expiresInDays(500).build())
                .lockAmount(lockAmount)
                .usagePriority(PriorityType.AfterCash)
                .rollover(generateRollover())
                .build();
        return Outcome.builder()
                .funds(funds)
                .build();
    }

    private Description generateDescription(List<String> languagesCode){
        String randomCode = randomNumeric(5);
        String internal = String.format("E2E - Int - %s", randomCode);
        ExternalDescription externalDescription = ExternalDescription.builder()
                .name(String.format("E2E - Ext - %s", randomCode))
                .description(String.format("E2E - Desc - %s", randomCode))
                .build();
        Map<String, ExternalDescription> external = new HashMap<>();
        languagesCode.forEach(code -> external.put(code, externalDescription));

        return Description.builder()
                .internal(internal)
                .external(external)
                .bonusType("FIRST_TIME_DEPOSIT")
                .build();
    }
    private Availability generateAvailability(UUID regionId){
        Amount amount = Amount.builder().type("FIXED").value(BigDecimal.valueOf(10)).build();

        Trigger trigger = Trigger.builder()
                .minAmount(ImmutableMap.of(properties.getCurrency(), amount))
                .paymentMethods(ImmutableSet.of("BT"))
                .build();


        return Availability.builder()
                .brandCode(properties.getBrandCode())
                .endsAt(Instant.now().plus(10, ChronoUnit.HOURS))
                .regionCode(regionId.toString())
                .startsAt(Instant.now().minus(1,ChronoUnit.DAYS))
                .trigger(trigger)
                .currencies(Sets.newSet(properties.getCurrency()))
                .useRestrictions(ImmutableSet.of(UseRestriction.builder().maxUses(1).build()))
                .build();
    }

    private UUID getRegionIdPerName(String brandCode, String regionName) {
        Optional<BrandRegion> region = referenceDataClient.readAllByBrand(brandCode).getItems().stream()
                .filter(brandRegion -> brandRegion.getName().equals(regionName))
                .findFirst();

        if (!region.isPresent()) throw new NotFoundException();
        return region.get().getId();
    }

    private List<String> getLanguagesCodePerBrandAndRegion(String brandCode, UUID regionId) {
        return brandPropertiesClient.getPropertiesPerRegion(brandCode,regionId.toString()).get("languages");
    }
}
