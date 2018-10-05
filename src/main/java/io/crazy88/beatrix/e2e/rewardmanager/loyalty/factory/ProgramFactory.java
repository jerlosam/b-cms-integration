package io.crazy88.beatrix.e2e.rewardmanager.loyalty.factory;

import static io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Brand.CRAZY88;
import static io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.ContributionType.HANDLE;
import static io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.PeriodStartType.ANNIVERSARY;
import static io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.ProgramStatus.ACTIVE;
import static io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.TokenType.CATEGORY;
import static io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.TokenType.GAME;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang.StringUtils.EMPTY;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.rewardmanager.loyalty.clients.BrandPropertiesClient;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.clients.ReferenceDataClient;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Brand;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Contribution;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.ContributionType;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.PeriodStartType;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Program;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.ProgramStatus;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Ratio;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Region;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Tier;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.TokenType;

@Component
public class ProgramFactory {

    public static final String EDITION_ADD_TEXT = " EDITED";

    public static final String REWARD_ID = "88fe022b-bd08-4a02-b7e3-dba99a0b5444";

    public static final String REWARD_NAME = "Crazy 88 Tier Default";

    public static final Long REWARD_LIFESPAN = 12L;

    private static final String REGION_NAME = "South America";

    private static final String PROGRAM_NAME = "Crazy 88 South America E2E Program - DO NOT DELETE";

    private static final String REWARD_POINTS_NAME = "Reward E2E";

    @Autowired
    private BrandPropertiesClient brandPropertiesClient;

    @Autowired
    private ReferenceDataClient referenceDataClient;

    public Program createProgram() {

        Region region = getRegion(CRAZY88.name(), REGION_NAME);

        Map<String, List<String>> properties = brandPropertiesClient.getPropertiesPerRegion(CRAZY88.name(), region.getCode());

        List<String> currencies = getOrEmptyList("currencies", properties);

        List<String> languages = getOrEmptyList("languages", properties);

        Map<String, String> names = generateNames(PROGRAM_NAME, languages);

        List<Contribution> contributions = generateContributions(currencies);

        List<Tier> tiers = generateTiers(languages, currencies);

        return createProgram(CRAZY88, region, null, names, null, null, null, REWARD_LIFESPAN,
                null, null, 15, null, currencies, null, contributions,
                tiers, null, null);
    }

    public Program createProgramForEdition() {

        Region region = getRegion(CRAZY88.name(), REGION_NAME);

        Map<String, List<String>> properties = brandPropertiesClient.getPropertiesPerRegion(CRAZY88.name(), region.getCode());

        List<String> countries = getOrEmptyList("countries", properties);

        List<String> currencies = getOrEmptyList("currencies", properties);

        List<String> languages = getOrEmptyList("languages", properties);

        Map<String, String> names = generateNames(PROGRAM_NAME, languages);

        Map<String, String> rewardPointsNames = generateNames(REWARD_POINTS_NAME, languages);

        List<Contribution> contributions = generateContributions(currencies);

        List<Tier> tiers = generateTiers(languages, currencies);

        return createProgram(CRAZY88, region, countries, names, ACTIVE, EMPTY, rewardPointsNames, REWARD_LIFESPAN,
                ANNIVERSARY, 1, 15, "USD", currencies, singletonList("CASINO"), contributions,
                tiers, Instant.now(), null);
    }

    private Program createProgram(final Brand brandForTesting, final Region region, final List<String> countries, final Map<String, String> names,
            final ProgramStatus status, final String link, final Map<String, String> rewardNames, final Long rewardLifespan,
            final PeriodStartType periodStartType, final Integer periodLength, final Integer periodStartingDate,
            final String currencyTemplate, final List<String> currencies, final List<String> products,
            final List<Contribution> contributions, final List<Tier> tiers, final Instant validFrom, final Instant validTo) {

        return Program.builder()
                .brandForTesting(brandForTesting)
                .brand(brandForTesting.name())
                .region(region)
                .names(names)
                .link(link)
                .status(status)
                .lifetime(false)
                .rewardPointsNames(rewardNames)
                .rewardPointsLifespan(rewardLifespan)
                .periodStartType(periodStartType)
                .periodLength(periodLength)
                .periodStartingDay(periodStartingDate)
                .countries(countries)
                .currencyTemplate(currencyTemplate)
                .currencies(currencies)
                .products(products)
                .contributions(contributions)
                .tiers(tiers)
                .validFrom(validFrom)
                .validTo(validTo)
                .build();
    }

    private List<Contribution> generateContributions(List<String> currencies) {

        final Map<String, Ratio> ratioGlobal = generateRatios(currencies, ONE, TEN);
        final Map<String, Ratio> ratioException = generateRatios(currencies, ONE, valueOf(20));

        return asList(
                generateContribution(HANDLE, "Table Game", "casino", "1", CATEGORY, ratioGlobal),
                generateContribution(HANDLE, null, "Table Game", "3", GAME, ratioException),
                generateContribution(HANDLE, "Slot Game", "casino", "3", CATEGORY, ratioGlobal)
        );
    }

    private Contribution generateContribution(final ContributionType contributionType, final String name, final String parent, final String token,
            final TokenType tokenType, final Map<String, Ratio> ratios) {
        return Contribution.builder()
                .type(contributionType)
                .name(name)
                .parent(parent)
                .token(token)
                .tokenType(tokenType)
                .ratios(ratios)
                .build();
    }

    private List<Tier> generateTiers(List<String> languages, List<String> currencies) {
        return asList(
                generateTier(generateNames("Bronze", languages),
                        REWARD_ID, REWARD_NAME, 0, 0, generateRatios(currencies, ONE, TEN)),
                generateTier(generateNames("Silver", languages),
                        REWARD_ID, REWARD_NAME, 1000, 1, generateRatios(currencies, ONE, valueOf(20)))
        );
    }

    private Tier generateTier(Map<String, String> names, String rewardId, String rewardName, int requiredPoints, int criteriaPoints,
            Map<String, Ratio> pointsToCash) {
        return Tier.builder()
                .names(names)
                .rewardId(rewardId)
                .rewardName(rewardName)
                .requiredPoints(requiredPoints)
                .requiredCriteriaPoints(criteriaPoints)
                .pointsToCash(pointsToCash)
                .build();
    }

    private Map<String, String> generateNames(String value, List<String> languages) {
        return languages.stream().collect(toMap(identity(), l -> value));
    }

    private Map<String, Ratio> generateRatios(List<String> currencies, BigDecimal points, BigDecimal amount) {
        return currencies.stream().collect(toMap(identity(), c -> Ratio.builder().points(points).amount(amount).build()));
    }

    private List<String> getOrEmptyList(String key, Map<String, List<String>> properties) {
        return properties.getOrDefault(key, emptyList());
    }

    private Region getRegion(String brandCode, String regionName) {
        return referenceDataClient.readAllByBrand(brandCode).getItems().stream()
                .filter(r -> r.getName().equals(regionName))
                .map(r -> Region.builder().code(r.getId().toString()).name(r.getName()).build())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Region not found"));
    }
}
