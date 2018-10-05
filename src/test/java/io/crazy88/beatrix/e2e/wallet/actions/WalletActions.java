package io.crazy88.beatrix.e2e.wallet.actions;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.clients.ProfileClient;
import io.crazy88.beatrix.e2e.clients.WalletGatewayClient;
import io.crazy88.beatrix.e2e.clients.dto.ProfileId;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import io.crazy88.beatrix.e2e.transactions.dto.Event;
import io.crazy88.beatrix.e2e.transactions.dto.Market;
import io.crazy88.beatrix.e2e.transactions.dto.Selection;
import io.crazy88.beatrix.e2e.transactions.dto.SportsData;
import io.crazy88.beatrix.e2e.transactions.dto.SportsMetadata;
import io.crazy88.beatrix.e2e.transactions.dto.Transaction;

@TestComponent
public class WalletActions {

    @Autowired
    private WalletGatewayClient walletGatewayClient;

    @Autowired
    private E2EProperties properties;

    @Autowired
    private ProfileClient profileClient;

    public String createAStatement(PlayerSignup playerSignup) {
        ProfileId profileId = profileClient.search(properties.getBrandCode(), playerSignup.getEmail()).get(0);
        List<Transaction> transactions = createSportsTransactions();
        String correlationRef = transactions.get(0).getCorrelationRef();
        walletGatewayClient.createTransaction(profileId.getProfileId(), transactions);
        return correlationRef;
    }

    private List<Transaction> createSportsTransactions() {

        return asList(Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .category("BGS-SB-PL")
                .amount(BigDecimal.valueOf(0.01))
                .currency(properties.getCurrency())
                .correlationRef(UUID.randomUUID().toString())
                .metadata(generateSportMetadata())
                .build());
    }

    private SportsMetadata generateSportMetadata() {
        Selection selection = Selection.builder()
                .description("Chicago Bulls")
                .price("+150")
                .spread(BigDecimal.ZERO)
                .market(Market.builder()
                        .description("Moneyline")
                        .note("Some market notes")
                        .build())
                .period("Match")
                .event(Event.builder()
                        .description("Chicago Bulls @ Denver Nuggets")
                        .path("Basketball - USA")
                        .note("Some notes...")
                        .date(Instant.now())
                        .build())
                .build();
        
        SportsData data = SportsData.builder()
                .txType("Placed")
                .betDescription("Single")
                .risk(BigDecimal.ONE)
                .win(BigDecimal.TEN)
                .currencyCode(properties.getCurrency())
                .selections(asList(selection))
                .build();
                
        
        
        return SportsMetadata.builder()
                .type("sports")
                .data(data)
                .build();
    }

}
