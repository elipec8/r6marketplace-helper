package github.ricemonger.ubi_users_stats_fetcher.services;


import github.ricemonger.ubi_users_stats_fetcher.services.configurations.UbiServiceConfiguration;
import github.ricemonger.utils.abstract_services.CommonValuesDatabaseService;
import github.ricemonger.utils.services.calculators.PricesCommonValuesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonValuesService implements PricesCommonValuesService {

    private final CommonValuesDatabaseService commonValuesDatabaseService;

    private final UbiServiceConfiguration ubiServiceConfiguration;

    public LocalDateTime getLastUbiUsersStatsFetchTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ubiServiceConfiguration.getDateFormat());
        try {
            return LocalDateTime.parse(commonValuesDatabaseService.getLastUbiUsersStatsFetchTime(), formatter);
        } catch (DateTimeParseException | NullPointerException e) {
            return LocalDateTime.now().minusDays(1).withNano(0);
        }
    }

    public void setLastUbiUsersStatsFetchTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ubiServiceConfiguration.getDateFormat());
        commonValuesDatabaseService.setLastUbiUsersStatsFetchTime(formatter.format(localDateTime));
    }

    public Integer getMinimumUncommonPrice() {
        return ubiServiceConfiguration.getMinUncommonPrice();
    }

    public Integer getMaximumUncommonPrice() {
        return ubiServiceConfiguration.getMaxUncommonPrice();
    }

    public Integer getMinimumRarePrice() {
        return ubiServiceConfiguration.getMinRarePrice();
    }

    public Integer getMaximumRarePrice() {
        return ubiServiceConfiguration.getMaxRarePrice();
    }

    public Integer getMinimumEpicPrice() {
        return ubiServiceConfiguration.getMinEpicPrice();
    }

    public Integer getMaximumEpicPrice() {
        return ubiServiceConfiguration.getMaxEpicPrice();
    }

    public Integer getMinimumLegendaryPrice() {
        return ubiServiceConfiguration.getMinLegendaryPrice();
    }

    public Integer getMaximumLegendaryPrice() {
        return ubiServiceConfiguration.getMaxLegendaryPrice();
    }

    public Integer getMinimumMarketplacePrice() {
        return Math.min(ubiServiceConfiguration.getMinUncommonPrice(), Math.min(ubiServiceConfiguration.getMinRarePrice(), Math.min(ubiServiceConfiguration.getMinEpicPrice(), ubiServiceConfiguration.getMinLegendaryPrice())));
    }

    public Integer getMaximumMarketplacePrice() {
        return Math.max(ubiServiceConfiguration.getMaxUncommonPrice(), Math.max(ubiServiceConfiguration.getMaxRarePrice(), Math.max(ubiServiceConfiguration.getMaxEpicPrice(), ubiServiceConfiguration.getMaxLegendaryPrice())));
    }
}
