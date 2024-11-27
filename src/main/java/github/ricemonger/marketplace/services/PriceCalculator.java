package github.ricemonger.marketplace.services;

import github.ricemonger.utils.DTOs.items.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PriceCalculator {

    public int getNextFancyBuyPriceByCurrentPrices(Item item) {
        return getNextFancyBuyPrice(item.getMaxBuyPrice(), item.getMinSellPrice());
    }

    public int getNextFancyBuyPrice(int buyPrice, int sellPrice) {
        if (buyPrice == 0) {
            return 10;
        } else if (sellPrice < 200) {
            return ((buyPrice + 10) / 10) * 10;
        } else if (sellPrice < 1000) {
            return ((buyPrice + 50) / 50) * 50;
        } else if (sellPrice < 3000) {
            return ((buyPrice + 100) / 100) * 100;
        } else if (sellPrice < 10_000) {
            return ((buyPrice + 500) / 500) * 500;
        } else if (sellPrice < 50_000) {
            return ((buyPrice + 1000) / 1000) * 1000;
        } else if (sellPrice < 200_000) {
            return ((buyPrice + 5000) / 5000) * 5000;
        } else {
            return ((buyPrice + 10_000) / 10_000) * 10_000;
        }
    }
}
