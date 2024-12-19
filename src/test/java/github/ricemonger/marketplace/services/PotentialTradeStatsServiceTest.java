package github.ricemonger.marketplace.services;

import github.ricemonger.utils.DTOs.items.Item;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PotentialTradeStatsServiceTest {
    @SpyBean
    private PotentialTradeStatsService potentialTradeStatsService;

    @Test
    public void getNextFancyBuyPriceByCurrentPrices_should_use_current_item_prices() {
        Item item = new Item();
        item.setMaxBuyPrice(10);
        item.setMinSellPrice(150);

        int price = potentialTradeStatsService.getNextFancyBuyPriceByCurrentPrices(item);

        verify(potentialTradeStatsService).getNextFancyBuyPrice(10, 150);

        assertEquals(20, price);
    }

    @Test
    public void getNextFancyBuyPrice_should_return_10_when_buy_price_is_0() {
        int price = potentialTradeStatsService.getNextFancyBuyPrice(0, 150);

        assertEquals(10, price);
    }

    @Test

    public void getNextFancyBuyPrice_should_return_next_10_when_sell_price_is_less_than_200() {
        int price = potentialTradeStatsService.getNextFancyBuyPrice(30, 150);

        assertEquals(40, price);
    }

    @Test
    public void getNextFancyBuyPrice_should_return_next_50_when_sell_price_is_less_than_1000() {
        int price = potentialTradeStatsService.getNextFancyBuyPrice(60, 500);

        assertEquals(100, price);
    }

    @Test
    public void getNextFancyBuyPrice_should_return_next_100_when_sell_price_is_less_than_3000() {
        int price = potentialTradeStatsService.getNextFancyBuyPrice(150, 2000);

        assertEquals(200, price);
    }

    @Test
    public void getNextFancyBuyPrice_should_return_next_500_when_sell_price_is_less_than_10000() {
        int price = potentialTradeStatsService.getNextFancyBuyPrice(600, 8000);

        assertEquals(1000, price);
    }

    @Test
    public void getNextFancyBuyPrice_should_return_next_1000_when_sell_price_is_less_than_50000() {
        int price = potentialTradeStatsService.getNextFancyBuyPrice(1500, 30000);

        assertEquals(2000, price);
    }

    @Test
    public void getNextFancyBuyPrice_should_return_next_5000_when_sell_price_is_less_than_200000() {
        int price = potentialTradeStatsService.getNextFancyBuyPrice(6000, 100000);

        assertEquals(10000, price);
    }

    @Test
    public void getNextFancyBuyPrice_should_return_next_10000_when_sell_price_is_more_than_200000() {
        int price = potentialTradeStatsService.getNextFancyBuyPrice(15000, 300000);

        assertEquals(20000, price);
    }
}