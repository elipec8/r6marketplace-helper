package github.ricemonger.trades_manager.services;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.services.calculators.ItemTradeTimeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static github.ricemonger.utils.services.calculators.ItemTradeTimeCalculator.*;

@Service
@RequiredArgsConstructor
public class PotentialTradeStatsService {

    private final CommonValuesService commonValuesService;

    private final ItemTradeTimeCalculator itemTradeTimeCalculator;

    public List<PotentialTradeStats> getPotentialSellTradesStatsOfItem(Item item) {
        List<PotentialTradeStats> result = new ArrayList<>();

        PotentialTradeStats nextFancySellPriceTradeStats = itemTradeTimeCalculator.calculatePriceAndTimeForNextFancySellPriceSale(item);
        result.add(new PotentialTradeStats(nextFancySellPriceTradeStats.getPrice(), nextFancySellPriceTradeStats.getTime()));

        if (item.getMaxBuyPrice() != null) {
            result.add(new PotentialTradeStats(item.getMaxBuyPrice(), TRADE_MANAGER_FIXED_RATE_MINUTES));
        }

        return result;
    }

    public List<PotentialTradeStats> getPotentialBuyTradesStatsOfItem(Item item) {
        List<PotentialTradeStats> result = new ArrayList<>();

        int immediateBuyPrice;
        if (item.getMinSellPrice() == null || item.getMinSellPrice() <= 0) {
            immediateBuyPrice = commonValuesService.getMinimumPriceByRarity(item.getRarity());
        } else {
            immediateBuyPrice = item.getMinSellPrice();
        }
        result.add(new PotentialTradeStats(immediateBuyPrice, TRADE_MANAGER_FIXED_RATE_MINUTES));

        if (item.getPriceToBuyIn1Hour() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn1Hour(), MINUTES_IN_AN_HOUR));
        }
        if (item.getPriceToBuyIn6Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn6Hours(), MINUTES_IN_6_HOURS));
        }
        if (item.getPriceToBuyIn24Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn24Hours(), MINUTES_IN_A_DAY));
        }
        if (item.getPriceToBuyIn168Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn168Hours(), MINUTES_IN_A_WEEK));
        }
        if (item.getPriceToBuyIn720Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn720Hours(), MINUTES_IN_A_MONTH));
        }

        return result;
    }

    public Integer calculateExpectedPaymentsSuccessMinutesForExistingTradeOrNull(UbiTrade existingTrade) {
        return itemTradeTimeCalculator.calculateExpectedPaymentsSuccessMinutesForExistingTradeOrNull(existingTrade);
    }
}
