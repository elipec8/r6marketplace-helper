package github.ricemonger.trades_manager.services.factories;

import github.ricemonger.trades_manager.services.CommonValuesService;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.PotentialTradePriceAndTimeStats;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PotentialTradeStatsService {
    public static final int MINUTES_IN_A_MONTH = 43200;
    public static final int MINUTES_IN_A_WEEK = 10080;
    public static final int MINUTES_IN_A_DAY = 1440;
    public static final int MINUTES_IN_6_HOURS = 360;
    public static final int MINUTES_IN_AN_HOUR = 60;
    public static final int TRADE_MANAGER_FIXED_RATE_MINUTES = 1;

    private final CommonValuesService commonValuesService;

    public List<PotentialTradeStats> getPotentialBuyTradesStatsOfItem(Item item) {
        List<PotentialTradeStats> result = new ArrayList<>();

        if (item.getPriorityToBuyByMinSellPrice() != null) {
            int price;
            if (item.getMinSellPrice() == null || item.getMinSellPrice() <= 0) {
                price = commonValuesService.getMinimumPriceByRarity(item.getRarity());
            } else {
                price = item.getMinSellPrice();
            }
            result.add(new PotentialTradeStats(price, TRADE_MANAGER_FIXED_RATE_MINUTES, item.getPriorityToBuyByMinSellPrice()));
        }
        if (item.getPriceToBuyIn1Hour() != null && item.getPriorityToBuyIn1Hour() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn1Hour(), MINUTES_IN_AN_HOUR, item.getPriorityToBuyIn1Hour()));
        }
        if (item.getPriceToBuyIn6Hours() != null && item.getPriorityToBuyIn6Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn6Hours(), MINUTES_IN_6_HOURS, item.getPriorityToBuyIn6Hours()));
        }
        if (item.getPriceToBuyIn24Hours() != null && item.getPriorityToBuyIn24Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn24Hours(), MINUTES_IN_A_DAY, item.getPriorityToBuyIn24Hours()));
        }
        if (item.getPriceToBuyIn168Hours() != null && item.getPriorityToBuyIn168Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn168Hours(), MINUTES_IN_A_WEEK, item.getPriorityToBuyIn168Hours()));
        }
        if (item.getPriceToBuyIn720Hours() != null && item.getPriorityToBuyIn720Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn720Hours(), MINUTES_IN_A_MONTH, item.getPriorityToBuyIn720Hours()));
        }

        return result;
    }

    public List<PotentialTradeStats> getPotentialSellTradesStatsOfItem(Item item) {
        List<PotentialTradeStats> result = new ArrayList<>();

        if (item.getPriorityToSellByNextFancySellPrice() != null) {
            PotentialTradePriceAndTimeStats priceAndTime = calculatePriceAndTimeForNextFancySellPriceSale(item);
            result.add(new PotentialTradeStats(priceAndTime.price(), priceAndTime.time(), item.getPriorityToSellByNextFancySellPrice()));
        }

        if (item.getPriorityToSellByMaxBuyPrice() != null && item.getMaxBuyPrice() != null) {
            result.add(new PotentialTradeStats(item.getMaxBuyPrice(), TRADE_MANAGER_FIXED_RATE_MINUTES, item.getPriorityToSellByMaxBuyPrice()));
        }

        return result;
    }

    public PotentialTradePriceAndTimeStats calculatePriceAndTimeForNextFancySellPriceSale(Item item) {
        int monthSalesPerDay = item.getMonthSalesPerDay() == null || item.getMonthSalesPerDay() <= 0 ? 1 : item.getMonthSalesPerDay();
        int nextFancySellPrice = getNextFancySellPrice(item);
        int timeToSellByNextFancySellPrice;
        if (item.getMinSellPrice() != null && item.getMinSellPrice() == commonValuesService.getMinimumPriceByRarity(item.getRarity())) {
            int sellOrdersCount = item.getSellOrdersCount() == null || item.getSellOrdersCount() <= 0 ? 1 : item.getSellOrdersCount();
            timeToSellByNextFancySellPrice = sellOrdersCount * MINUTES_IN_A_DAY / monthSalesPerDay;
        } else {
            timeToSellByNextFancySellPrice = MINUTES_IN_A_DAY / monthSalesPerDay;
        }

        return new PotentialTradePriceAndTimeStats(nextFancySellPrice, timeToSellByNextFancySellPrice);
    }

    private int getNextFancySellPrice(Item item) {
        int limitMaxPrice = commonValuesService.getMaximumPriceByRarity(item.getRarity());

        int limitMinPrice = commonValuesService.getMinimumPriceByRarity(item.getRarity());

        if (item.getMinSellPrice() == null || item.getMinSellPrice() <= 0) {
            return limitMaxPrice;
        } else {
            return Math.max(limitMinPrice, item.getMinSellPrice() - 1);
        }
    }
}
