package github.ricemonger.fast_sell_trade_manager.services.factories;

import github.ricemonger.fast_sell_trade_manager.services.CommonValuesService;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.PotentialTrade;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PotentialTradeFactory {

    private final CommonValuesService commonValuesService;

    public List<PotentialTrade> createPotentialTradesForUser(Collection<ItemCurrentPrices> itemsCurrentPrices,
                                                             Collection<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity,
                                                             Integer minMedianPriceDifference,
                                                             Integer minMedianPriceDifferencePercentage) {
        List<PotentialTrade> potentialTrade = new ArrayList<>();

        for (ItemCurrentPrices itemPrices : itemsCurrentPrices) {
            ItemMedianPriceAndRarity itemMedianPriceAndRarity = itemsMedianPriceAndRarity.stream()
                    .filter(item -> item.getItemId().equals(itemPrices.getItemId()))
                    .findFirst()
                    .orElse(null);

            if (itemMedianPriceAndRarity != null && itemMedianPriceAndRarity.getMonthMedianPrice() != null) {
                PotentialTrade trade = createPotentialTradeForUserOrNull(itemPrices, itemMedianPriceAndRarity, minMedianPriceDifference, minMedianPriceDifferencePercentage);
                if (trade != null) {
                    potentialTrade.add(trade);
                }
            }
        }
        return potentialTrade;
    }

    public PotentialTrade createPotentialTradeForUserOrNull(@NonNull ItemCurrentPrices itemsCurrentPrices,
                                                            @NonNull ItemMedianPriceAndRarity itemMedianPriceAndRarity,
                                                            @NonNull Integer minMedianPriceDifference,
                                                            @NonNull Integer minMedianPriceDifferencePercentage) {

        if (itemsCurrentPrices.getMaxBuyPrice() != null) {
            Integer buyPriceMedianPriceDifference = itemsCurrentPrices.getMaxBuyPrice() - itemMedianPriceAndRarity.getMonthMedianPrice();
            Integer buyPriceMedianPriceDifferencePercentage = (buyPriceMedianPriceDifference * 100) / itemMedianPriceAndRarity.getMonthMedianPrice();

            if (buyPriceMedianPriceDifference >= minMedianPriceDifference && buyPriceMedianPriceDifferencePercentage >= minMedianPriceDifferencePercentage) {
                return new PotentialTrade(
                        itemsCurrentPrices.getItemId(),
                        itemsCurrentPrices.getMaxBuyPrice() - 1,
                        buyPriceMedianPriceDifference,
                        buyPriceMedianPriceDifferencePercentage,
                        true
                );
            }
        }
        int sellPrice = itemsCurrentPrices.getMinSellPrice() == null ? commonValuesService.getMaximumPriceByRarity(itemMedianPriceAndRarity.getRarity()) : itemsCurrentPrices.getMinSellPrice();

        Integer sellPriceMedianPriceDifference = sellPrice - itemMedianPriceAndRarity.getMonthMedianPrice();
        Integer sellPriceMedianPriceDifferencePercentage = (sellPriceMedianPriceDifference * 100) / itemMedianPriceAndRarity.getMonthMedianPrice();

        if (sellPriceMedianPriceDifference >= minMedianPriceDifference && sellPriceMedianPriceDifferencePercentage >= minMedianPriceDifferencePercentage) {
            return new PotentialTrade(
                    itemsCurrentPrices.getItemId(),
                    sellPrice - 1,
                    sellPriceMedianPriceDifference,
                    sellPriceMedianPriceDifferencePercentage,
                    false
            );
        }

        return null;
    }
}
