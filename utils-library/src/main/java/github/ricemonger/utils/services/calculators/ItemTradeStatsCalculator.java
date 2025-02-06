package github.ricemonger.utils.services.calculators;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemDaySalesStatsByItemId;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.common.PrioritizedPotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.UbiTradeI;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static github.ricemonger.utils.services.calculators.ItemTradeTimeCalculator.MINUTES_IN_A_MONTH;
import static github.ricemonger.utils.services.calculators.ItemTradeTimeCalculator.TRADE_MANAGER_FIXED_RATE_MINUTES;

@RequiredArgsConstructor
public class ItemTradeStatsCalculator {
    private final ItemFancyPriceCalculator itemFancyPriceCalculator;

    private final ItemTradeTimeCalculator itemTradeTimeCalculator;

    private final ItemTradePriorityCalculator itemTradePriorityCalculator;

    public PrioritizedPotentialTradeStats calculatePotentialSellTradeStatsByMaxBuyPrice(Item item) {
        return calculatePotentialSellTradeStats(item, item.getMaxBuyPrice(), TRADE_MANAGER_FIXED_RATE_MINUTES);
    }

    public PrioritizedPotentialTradeStats calculatePotentialSellTradeStatsByNextFancySellPrice(Item item) {
        PotentialTradeStats priceAndTime = itemTradeTimeCalculator.calculatePriceAndTimeForNextFancySellPriceSale(item);
        return calculatePotentialSellTradeStats(item, priceAndTime.price(), priceAndTime.time());
    }

    public PrioritizedPotentialTradeStats calculatePotentialBuyTradeStatsByMinSellPrice(Item item) {
        return calculatePotentialBuyTradeStats(item, item.getMinSellPrice(), TRADE_MANAGER_FIXED_RATE_MINUTES);
    }

    public PrioritizedPotentialTradeStats calculatePotentialBuyTradeStatsForTime(Item item, Collection<ItemDaySalesStatsByItemId> resultingPerDayStats, Integer minutesToBuy) {
        int price = calculatePotentialBuyTradePriceForTime(item, resultingPerDayStats, minutesToBuy);

        return calculatePotentialBuyTradeStats(item, price, minutesToBuy);
    }

    public Integer calculatePotentialBuyTradePriceForTime(Item item, Collection<ItemDaySalesStatsByItemId> resultingPerDayStats, Integer minutesToBuy) {
        TreeMap<Integer, Integer> sortedMonthPricesAndQuantities = new TreeMap<>(Comparator.naturalOrder());
        for (ItemDaySalesStatsByItemId dayStat : resultingPerDayStats) {
            for (Map.Entry<Integer, Integer> priceAndQuantity : dayStat.getPriceAndQuantity().entrySet()) {
                sortedMonthPricesAndQuantities.put(priceAndQuantity.getKey(), sortedMonthPricesAndQuantities.getOrDefault(priceAndQuantity.getKey(), 0) + priceAndQuantity.getValue());
            }
        }

        int basicRequiredSalesAmount = MINUTES_IN_A_MONTH / minutesToBuy;

        Iterator<Map.Entry<Integer, Integer>> iterator = sortedMonthPricesAndQuantities.entrySet().iterator();

        int totalAmount = 0;

        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();

            int price = entry.getKey();
            int quantity = entry.getValue();

            totalAmount += quantity;

            if (basicRequiredSalesAmount + itemTradeTimeCalculator.getSameOrHigherPricesBuyOrdersAmount(item, price) <= totalAmount) {
                itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, price);
            }
        }

        return null;
    }

    public PrioritizedPotentialTradeStats calculatePotentialSellTradeStatsForExistingTrade(UbiTradeI existingTrade) {
        return calculatePotentialSellTradeStats(existingTrade.getItem(), existingTrade.getProposedPaymentPrice(), itemTradeTimeCalculator.getExpectedPaymentsSuccessMinutesForExistingTradeOrNull(existingTrade));
    }

    public PrioritizedPotentialTradeStats calculatePotentialBuyTradeStatsForExistingTrade(UbiTradeI existingTrade) {
        return calculatePotentialBuyTradeStats(existingTrade.getItem(), existingTrade.getProposedPaymentPrice(), itemTradeTimeCalculator.getExpectedPaymentsSuccessMinutesForExistingTradeOrNull(existingTrade));
    }

    public PrioritizedPotentialTradeStats calculatePotentialSellTradeStats(Item item, Integer price, Integer minutesToTrade) {
        return new PrioritizedPotentialTradeStats(price, minutesToTrade, itemTradePriorityCalculator.calculatePotentialSellTradePriority(item, price, minutesToTrade));
    }

    public PrioritizedPotentialTradeStats calculatePotentialBuyTradeStats(Item item, Integer price, Integer minutesToTrade) {
        return new PrioritizedPotentialTradeStats(price, minutesToTrade, itemTradePriorityCalculator.calculatePotentialBuyTradePriority(item, price, minutesToTrade));
    }
}
