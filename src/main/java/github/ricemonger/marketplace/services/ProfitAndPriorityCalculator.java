package github.ricemonger.marketplace.services;

import github.ricemonger.utils.dtos.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfitAndPriorityCalculator {

    public int calculateItemProfit(Item item) {
        return getExpectedProfit(item);
    }

    public int calculateItemProfitPercents(Item item) {
        return getExpectedProfitPercentage(item);
    }

    public int calculateNextBuyPrice(Item item) {
        return getNextFancyBuyPrice(item.getMaxBuyPrice(), item.getMinSellPrice());
    }

    private int getExpectedProfitPercentage(Item item) {
        return (getExpectedProfit(item) * 100) / getNextFancyBuyPrice(item.getMaxBuyPrice(), item.getMinSellPrice());
    }

    private int getExpectedProfit(Item item) {
        return item.getMinSellPrice() - getNextFancyBuyPrice(item.getMaxBuyPrice(), item.getMinSellPrice());
    }

    private int getNextFancyBuyPrice(int buyPrice, int sellPrice) {
        if (buyPrice == 0) {
            return 120;
        } else if (sellPrice < 200) {
            return ((buyPrice + 10) / 10) * 10;
        } else if (sellPrice < 1000) {
            return ((buyPrice + 50) / 50) * 50;
        } else if (sellPrice < 3000) {
            return ((buyPrice + 100) / 100) * 100;
        } else if (sellPrice < 10000) {
            return ((buyPrice + 500) / 500) * 500;
        } else if (sellPrice < 50000) {
            return ((buyPrice + 1000) / 1000) * 1000;
        } else {
            return ((buyPrice + 5000) / 5000) * 5000;
        }
    }
}
