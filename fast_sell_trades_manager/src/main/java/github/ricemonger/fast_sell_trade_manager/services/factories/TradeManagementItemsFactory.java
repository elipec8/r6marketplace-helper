package github.ricemonger.fast_sell_trade_manager.services.factories;

import github.ricemonger.fast_sell_trade_manager.services.CommonValuesService;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.PotentialTrade;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeManagementItemsFactory {

    private final CommonValuesService commonValuesService;

    public List<PotentialTrade> createFilteredTradeManagementItemsForUser(Collection<ItemCurrentPrices> itemsCurrentPrices,
                                                                          Collection<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity,
                                                                          Integer minMedianPriceDifference,
                                                                          Integer minMedianPriceDifferencePercentage) {
        List<PotentialTrade> potentialTrade = new ArrayList<>();

        for (ItemCurrentPrices itemPrices : itemsCurrentPrices) {
            ItemMedianPriceAndRarity itemMedianPriceAndRarity = itemsMedianPriceAndRarity.stream()
                    .filter(item -> item.getItemId().equals(itemPrices.getItemId()))
                    .findFirst()
                    .orElse(null);

            if (itemMedianPriceAndRarity != null && itemPrices.getMaxBuyPrice() != null) {
                Integer buyPriceMedianPriceDifference = itemPrices.getMaxBuyPrice() - itemMedianPriceAndRarity.getMonthMedianPrice();
                Integer buyPriceMedianPriceDifferencePercentage = (buyPriceMedianPriceDifference * 100) / itemPrices.getMaxBuyPrice();

                if (buyPriceMedianPriceDifference >= minMedianPriceDifference && buyPriceMedianPriceDifferencePercentage >= minMedianPriceDifferencePercentage) {
                    potentialTrade.add(new PotentialTrade(
                            itemPrices.getItemId(),
                            itemPrices.getMaxBuyPrice() - 1,
                            buyPriceMedianPriceDifference,
                            buyPriceMedianPriceDifferencePercentage,
                            true
                    ));
                }
            } else if (itemMedianPriceAndRarity != null) {
                int sellPrice = itemPrices.getMinSellPrice() == null ? commonValuesService.getMaximumPriceByRarity(itemMedianPriceAndRarity.getRarity()) : itemPrices.getMinSellPrice();

                Integer sellPriceMedianPriceDifference = sellPrice - itemMedianPriceAndRarity.getMonthMedianPrice();
                Integer sellPriceMedianPriceDifferencePercentage = (sellPriceMedianPriceDifference * 100) / sellPrice;

                if (sellPriceMedianPriceDifference >= minMedianPriceDifference && sellPriceMedianPriceDifferencePercentage >= minMedianPriceDifferencePercentage) {
                    potentialTrade.add(new PotentialTrade(
                            itemPrices.getItemId(),
                            sellPrice - 1,
                            sellPriceMedianPriceDifference,
                            sellPriceMedianPriceDifferencePercentage,
                            false
                    ));
                }
            }
        }
        return potentialTrade;
    }
}
