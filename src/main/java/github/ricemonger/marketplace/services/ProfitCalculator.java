package github.ricemonger.marketplace.services;

import github.ricemonger.utils.dtos.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfitCalculator {

    private final PriceCalculator priceCalculator;


    private int getProfitByCurrentPriceDifferencePercentage(Item item) {
        return (getProfitByCurrentPriceDifference(item) * 100) / priceCalculator.getNextFancyBuyPrice(item.getMaxBuyPrice(), item.getMinSellPrice());
    }

    private int getProfitByCurrentPriceDifference(Item item) {
        return item.getMinSellPrice() - priceCalculator.getNextFancyBuyPrice(item.getMaxBuyPrice(), item.getMinSellPrice());
    }
}
