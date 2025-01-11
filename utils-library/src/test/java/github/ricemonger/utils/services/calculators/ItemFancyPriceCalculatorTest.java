package github.ricemonger.utils.services.calculators;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.enums.ItemRarity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ItemFancyPriceCalculatorTest {

    private final CalculatorsCommonValuesService commonValuesService = mock(CalculatorsCommonValuesService.class);

    private final ItemFancyPriceCalculator itemFancyPriceCalculator = spy(new ItemFancyPriceCalculator(commonValuesService));

    @Test
    public void getNextFancySellPrice_should_return_limitMaxPrice_when_itemMinSellPrice_is_null() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMaximumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getNextFancySellPrice(item);

        assertEquals(100, result);
    }

    @Test
    public void getNextFancySellPrice_should_return_limitMaxPrice_when_itemMinSellPrice_is_0() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(0);

        when(commonValuesService.getMaximumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);
        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(10);

        int result = itemFancyPriceCalculator.getNextFancySellPrice(item);

        assertEquals(100, result);
    }

    @Test
    public void getNextFancySellPrice_should_return_limitMinPrice_when_itemMinSellPrice_is_min() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(100);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);
        when(commonValuesService.getMaximumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(1000);

        int result = itemFancyPriceCalculator.getNextFancySellPrice(item);

        assertEquals(100, result);
    }

    @Test
    public void getNextFancySellPrice_should_return_limitMaxPrice_when_itemMinSellPrice_is_less_than_min() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(99);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);
        when(commonValuesService.getMaximumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(1000);

        int result = itemFancyPriceCalculator.getNextFancySellPrice(item);

        assertEquals(1000, result);
    }

    @Test
    public void getNextFancySellPrice_should_return_minus_one_minSellPrice_when_itemMinSellPrice_is_normal() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(1000);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getNextFancySellPrice(item);

        assertEquals(999, result);
    }

    @Test
    public void getPrevFancyBuyPriceByMaxBuyPrice_should_return_getPrevFancyBuyPrice_with_maxBuyPrice_result() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMaxBuyPrice(1000);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        doReturn(555).when(itemFancyPriceCalculator).getPrevFancyBuyPrice(item, 1000);

        assertEquals(555, itemFancyPriceCalculator.getPrevFancyBuyPriceByMaxBuyPrice(item));

        verify(itemFancyPriceCalculator).getPrevFancyBuyPrice(item, 1000);
    }

    @Test
    public void getPrevFancyBuyPrice_should_return_limitMinPrice_when_buyPrice_is_null() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getPrevFancyBuyPrice(item, null);

        assertEquals(100, result);
    }

    @Test
    public void getPrevFancyBuyPrice_should_return_limitMinPrice_when_buyPrice_is_lower_than_min() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getPrevFancyBuyPrice(item, 99);

        assertEquals(100, result);
    }

    @Test
    public void getPrevFancyBuyPrice_should_return_expected_result_if_buyPrice_lower_than_200() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getPrevFancyBuyPrice(item, 195);

        assertEquals(180, result);
    }

    @Test
    public void getPrevFancyBuyPrice_should_return_expected_result_if_buyPrice_lower_than_1000() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getPrevFancyBuyPrice(item, 999);

        assertEquals(900, result);
    }

    @Test
    public void getPrevFancyBuyPrice_should_return_expected_result_if_buyPrice_lower_than_3000() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getPrevFancyBuyPrice(item, 2999);

        assertEquals(2800, result);
    }

    @Test
    public void getPrevFancyBuyPrice_should_return_expected_result_if_buyPrice_lower_than_10000() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getPrevFancyBuyPrice(item, 9999);

        assertEquals(9000, result);
    }

    @Test
    public void getPrevFancyBuyPrice_should_return_expected_result_if_buyPrice_lower_than_50000() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getPrevFancyBuyPrice(item, 49999);

        assertEquals(48000, result);
    }

    @Test
    public void getPrevFancyBuyPrice_should_return_expected_result_if_buyPrice_lower_than_200000() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getPrevFancyBuyPrice(item, 199999);

        assertEquals(190000, result);
    }

    @Test
    public void getPrevFancyBuyPrice_should_return_expected_result_if_buyPrice_higher_than_200000() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getPrevFancyBuyPrice(item, 989_999);

        assertEquals(970_000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_limitMinPrice_for_null_buyPrice() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, null);

        assertEquals(100, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_limitMinPrice_for_lower_than_min_buyPrice() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 99);

        assertEquals(100, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_200_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(199);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 190);

        assertEquals(190, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_200_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(199);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 195);

        assertEquals(200, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_1000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 950);

        assertEquals(950, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_1000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 949);

        assertEquals(950, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_3000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(2999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 2900);

        assertEquals(2900, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_3000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(2999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 2899);

        assertEquals(2900, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_10000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(9999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 9500);

        assertEquals(9500, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_10000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(9999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 9499);

        assertEquals(9500, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_50000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(49999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 48000);

        assertEquals(48000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_50000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(49999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 47999);

        assertEquals(48000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_200000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(199999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 190000);

        assertEquals(190000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_lower_than_200000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(199999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 189999);

        assertEquals(190000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_higher_than_200000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(989_999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 970_000);

        assertEquals(970_000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_sellPrice_higher_than_200000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(989_999);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 969_999);

        assertEquals(970_000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_200_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 190);

        assertEquals(190, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_200_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 195);

        assertEquals(200, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_1000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 950);

        assertEquals(950, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_1000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 949);

        assertEquals(950, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_3000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 2900);

        assertEquals(2900, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_3000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 2899);

        assertEquals(2900, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_10000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 9500);

        assertEquals(9500, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_10000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 9499);

        assertEquals(9500, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_50000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 48000);

        assertEquals(48000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_50000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 47999);

        assertEquals(48000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_200000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 190000);

        assertEquals(190000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_lower_than_200000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 189999);

        assertEquals(190000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_higher_than_200000_and_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 970_000);

        assertEquals(970_000, result);
    }

    @Test
    public void getCurrentFancyBuyPrice_should_return_expected_result_for_buyPrice_higher_than_200000_and_non_fancy() {
        Item item = new Item();
        item.setRarity(ItemRarity.UNCOMMON);
        item.setMinSellPrice(null);

        when(commonValuesService.getMinimumPriceByRarity(ItemRarity.UNCOMMON)).thenReturn(100);

        int result = itemFancyPriceCalculator.getCurrentFancyBuyPrice(item, 969_999);

        assertEquals(970_000, result);
    }
}