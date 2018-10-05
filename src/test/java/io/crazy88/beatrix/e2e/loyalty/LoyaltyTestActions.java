package io.crazy88.beatrix.e2e.loyalty;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import io.crazy88.beatrix.boot.service.exception.NotFoundException;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.clients.BrandPropertiesClient;
import io.crazy88.beatrix.e2e.clients.ReferenceDataClient;
import io.crazy88.beatrix.e2e.clients.dto.BrandRegion;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.components.ExistingProgramsComponent;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.components.LoyaltyHeaderComponent;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Program;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
@Slf4j
public class LoyaltyTestActions {

    @Autowired
    private BrandPropertiesClient brandPropertiesClient;

    @Autowired
    private LoyaltyHeaderComponent loyaltyHeaderComponent;

    @Autowired
    private ExistingProgramsComponent existingProgramsComponent;

    @Autowired
    private ReferenceDataClient referenceDataClient;

    @Autowired
    private E2EProperties properties;

    public boolean isLoyaltyHeaderDisplayed() {
        return loyaltyHeaderComponent.isDisplayed();
    }

    public boolean isLoyaltyProgramCallSuccessful() {
        return existingProgramsComponent.isLoyaltyProgramCallSuccessful();
    }

    public boolean isProgramDisplayed(Program program, boolean afterEdition) {
        return existingProgramsComponent.isProgramDisplayed(program, afterEdition);
    }

    private UUID getRegionId(String brandCode, String regionName) {
        return referenceDataClient.readAllByBrand(brandCode).getItems().stream()
                .filter(brandRegion -> brandRegion.getName().equals(regionName))
                .map(BrandRegion::getId)
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
