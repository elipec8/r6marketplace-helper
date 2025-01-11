package github.ricemonger.item_trade_stats_calculator.services;

import github.ricemonger.item_trade_stats_calculator.services.configurations.UbiServiceConfiguration;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.services.calculators.CalculatorsCommonValuesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonValuesService implements CalculatorsCommonValuesService {

    private final UbiServiceConfiguration ubiServiceConfiguration;

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
