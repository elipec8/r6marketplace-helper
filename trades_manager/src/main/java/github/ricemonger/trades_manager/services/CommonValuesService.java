package github.ricemonger.trades_manager.services;

import github.ricemonger.trades_manager.services.configurations.UbiServiceConfiguration;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.abstractions.CommonValuesDatabaseService;
import github.ricemonger.utils.enums.ItemRarity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonValuesService {

    private final CommonValuesDatabaseService commonValuesDatabaseService;

    private final UbiServiceConfiguration ubiServiceConfiguration;

    public ConfigTrades getConfigTrades() {
        return commonValuesDatabaseService.getConfigTrades();
    }

    public int getMinimumPriceByRarity(ItemRarity rarity) {
        if (rarity == null) {
            return getMinimumMarketplacePrice();
        }
        return switch (rarity) {
            case UNCOMMON -> getMinimumUncommonPrice();
            case RARE -> getMinimumRarePrice();
            case EPIC -> getMinimumEpicPrice();
            case LEGENDARY -> getMinimumLegendaryPrice();
            default -> getMinimumLegendaryPrice();
        };
    }

    public int getMaximumPriceByRarity(ItemRarity rarity) {
        if (rarity == null) {
            return getMaximumMarketplacePrice();
        }
        return switch (rarity) {
            case UNCOMMON -> getMaximumUncommonPrice();
            case RARE -> getMaximumRarePrice();
            case EPIC -> getMaximumEpicPrice();
            case LEGENDARY -> getMaximumLegendaryPrice();
            default -> getMaximumUncommonPrice();
        };
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
