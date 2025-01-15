package github.ricemonger.fast_sell_trade_manager.services.factories;

import github.ricemonger.fast_sell_trade_manager.services.CommonValuesService;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.PotentialTrade;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import github.ricemonger.utils.enums.ItemRarity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PotentialTradeFactoryTest {
    @Autowired
    private PotentialTradeFactory potentialTradeFactory;
    @MockBean
    private CommonValuesService commonValuesService;

    @Test
    public void createPotentialTradeForUserOrNull_should_create_expected_potential_trade_for_edge_buy_price_and_high_sell_price(){
        ItemCurrentPrices currentPrices = new ItemCurrentPrices();
        currentPrices.setItemId("itemId");
        currentPrices.setMaxBuyPrice(1000);
        currentPrices.setMinSellPrice(100000);
        ItemMedianPriceAndRarity medianPriceAndRarity = new ItemMedianPriceAndRarity();
        medianPriceAndRarity.setMonthMedianPrice(500);
        int minMedianPriceDifference = 500;
        int minMedianPriceDifferencePercentage = 100;

        PotentialTrade trade = potentialTradeFactory.createPotentialTradeForUserOrNull(currentPrices, medianPriceAndRarity, minMedianPriceDifference, minMedianPriceDifferencePercentage);

        PotentialTrade expected = new PotentialTrade("itemId", 999, 500, 100, true);

        assertEquals(expected, trade);
    }

    @Test
    public void createPotentialTradeForUserOrNull_should_create_expected_potential_trade_for_buy_price_edge(){
        ItemCurrentPrices currentPrices = new ItemCurrentPrices();
        currentPrices.setItemId("itemId");
        currentPrices.setMaxBuyPrice(1000);
        currentPrices.setMinSellPrice(10);
        ItemMedianPriceAndRarity medianPriceAndRarity = new ItemMedianPriceAndRarity();
        medianPriceAndRarity.setMonthMedianPrice(500);
        int minMedianPriceDifference = 500;
        int minMedianPriceDifferencePercentage = 100;

        PotentialTrade trade = potentialTradeFactory.createPotentialTradeForUserOrNull(currentPrices, medianPriceAndRarity, minMedianPriceDifference, minMedianPriceDifferencePercentage);

        PotentialTrade expected = new PotentialTrade("itemId", 999, 500, 100, true);

        assertEquals(expected, trade);
    }

    @Test
    public void createPotentialTradeForUserOrNull_should_create_expected_potential_trade_for_buy_price_no_edge(){
        ItemCurrentPrices currentPrices = new ItemCurrentPrices();
        currentPrices.setItemId("itemId");
        currentPrices.setMaxBuyPrice(1500);
        currentPrices.setMinSellPrice(10000);
        ItemMedianPriceAndRarity medianPriceAndRarity = new ItemMedianPriceAndRarity();
        medianPriceAndRarity.setMonthMedianPrice(500);
        int minMedianPriceDifference = 500;
        int minMedianPriceDifferencePercentage = 100;

        PotentialTrade trade = potentialTradeFactory.createPotentialTradeForUserOrNull(currentPrices, medianPriceAndRarity, minMedianPriceDifference, minMedianPriceDifferencePercentage);

        PotentialTrade expected = new PotentialTrade("itemId", 1499, 1000, 200, true);

        assertEquals(expected, trade);
    }

    @Test
    public void createPotentialTradeForUserOrNull_should_create_expected_potential_trade_for_sell_price_edge(){
        ItemCurrentPrices currentPrices = new ItemCurrentPrices();
        currentPrices.setItemId("itemId");
        currentPrices.setMaxBuyPrice(999);
        currentPrices.setMinSellPrice(1000);
        ItemMedianPriceAndRarity medianPriceAndRarity = new ItemMedianPriceAndRarity();
        medianPriceAndRarity.setMonthMedianPrice(500);
        int minMedianPriceDifference = 500;
        int minMedianPriceDifferencePercentage = 100;

        PotentialTrade trade = potentialTradeFactory.createPotentialTradeForUserOrNull(currentPrices, medianPriceAndRarity, minMedianPriceDifference, minMedianPriceDifferencePercentage);

        PotentialTrade expected = new PotentialTrade("itemId", 999, 500, 100, false);

        assertEquals(expected, trade);
    }

    @Test
    public void createPotentialTradeForUserOrNull_should_create_expected_potential_trade_for_sell_price_no_edge(){
        ItemCurrentPrices currentPrices = new ItemCurrentPrices();
        currentPrices.setItemId("itemId");
        currentPrices.setMaxBuyPrice(null);
        currentPrices.setMinSellPrice(1500);
        ItemMedianPriceAndRarity medianPriceAndRarity = new ItemMedianPriceAndRarity();
        medianPriceAndRarity.setMonthMedianPrice(500);
        int minMedianPriceDifference = 500;
        int minMedianPriceDifferencePercentage = 100;

        PotentialTrade trade = potentialTradeFactory.createPotentialTradeForUserOrNull(currentPrices, medianPriceAndRarity, minMedianPriceDifference, minMedianPriceDifferencePercentage);

        PotentialTrade expected = new PotentialTrade("itemId", 1499, 1000, 200, false);

        assertEquals(expected, trade);
    }

    @Test
    public void createPotentialTradeForUserOrNull_should_create_expected_potential_trade_for_null_sell_price(){
        ItemCurrentPrices currentPrices = new ItemCurrentPrices();
        currentPrices.setItemId("itemId");
        currentPrices.setMaxBuyPrice(1500);
        currentPrices.setMinSellPrice(null);
        ItemMedianPriceAndRarity medianPriceAndRarity = new ItemMedianPriceAndRarity();
        medianPriceAndRarity.setRarity(ItemRarity.RARE);
        medianPriceAndRarity.setMonthMedianPrice(500);
        int minMedianPriceDifference = 500;
        int minMedianPriceDifferencePercentage = 100;

        when(commonValuesService.getMaximumPriceByRarity(ItemRarity.RARE)).thenReturn(10000);

        PotentialTrade trade = potentialTradeFactory.createPotentialTradeForUserOrNull(currentPrices, medianPriceAndRarity, minMedianPriceDifference, minMedianPriceDifferencePercentage);

        PotentialTrade expected = new PotentialTrade("itemId", 1499, 1000, 200, true);

        assertEquals(expected, trade);
    }

    @Test
    public void createPotentialTradeForUserOrNull_should_return_null_if_low_prices(){
        ItemCurrentPrices currentPrices = new ItemCurrentPrices();
        currentPrices.setItemId("itemId");
        currentPrices.setMaxBuyPrice(999);
        currentPrices.setMinSellPrice(999);
        ItemMedianPriceAndRarity medianPriceAndRarity = new ItemMedianPriceAndRarity();
        medianPriceAndRarity.setMonthMedianPrice(500);
        int minMedianPriceDifference = 500;
        int minMedianPriceDifferencePercentage = 100;

        PotentialTrade trade = potentialTradeFactory.createPotentialTradeForUserOrNull(currentPrices, medianPriceAndRarity, minMedianPriceDifference, minMedianPriceDifferencePercentage);

        assertNull(trade);
    }

}