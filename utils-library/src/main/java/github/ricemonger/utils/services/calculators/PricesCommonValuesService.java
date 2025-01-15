package github.ricemonger.utils.services.calculators;

import github.ricemonger.utils.enums.ItemRarity;

public interface PricesCommonValuesService {

    default Integer getMinimumPriceByRarity(ItemRarity rarity) {
        if (rarity == null) {
            return getMinimumLegendaryPrice();
        }
        return switch (rarity) {
            case UNCOMMON -> getMinimumUncommonPrice();
            case RARE -> getMinimumRarePrice();
            case EPIC -> getMinimumEpicPrice();
            case LEGENDARY -> getMinimumLegendaryPrice();
            default -> getMinimumLegendaryPrice();
        };
    }

    default Integer getMaximumPriceByRarity(ItemRarity rarity) {
        if (rarity == null) {
            return getMaximumUncommonPrice();
        }
        return switch (rarity) {
            case UNCOMMON -> getMaximumUncommonPrice();
            case RARE -> getMaximumRarePrice();
            case EPIC -> getMaximumEpicPrice();
            case LEGENDARY -> getMaximumLegendaryPrice();
            default -> getMaximumUncommonPrice();
        };
    }

    default Integer getMinimumMarketplacePrice() {
        return Math.min(getMinimumUncommonPrice(), Math.min(getMinimumRarePrice(), Math.min(getMinimumEpicPrice(), getMinimumLegendaryPrice())));
    }

    default Integer getMaximumMarketplacePrice() {
        return Math.max(getMaximumUncommonPrice(), Math.max(getMaximumRarePrice(), Math.max(getMaximumEpicPrice(), getMaximumLegendaryPrice())));
    }

    Integer getMinimumUncommonPrice();

    Integer getMaximumUncommonPrice();

    Integer getMinimumRarePrice();

    Integer getMaximumRarePrice();

    Integer getMinimumEpicPrice();

    Integer getMaximumEpicPrice();

    Integer getMinimumLegendaryPrice();

    Integer getMaximumLegendaryPrice();
}
