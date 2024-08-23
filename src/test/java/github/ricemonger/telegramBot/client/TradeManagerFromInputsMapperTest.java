package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.services.ProfitAndPriorityCalculator;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.*;
import github.ricemonger.utils.enums.TradeManagerTradeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeManagerFromInputsMapperTest {
    @Autowired
    private TradeManagerFromInputsMapper tradeManagerFromInputsMapper;
    @MockBean
    private ProfitAndPriorityCalculator profitAndPriorityCalculator;

    @Test
    public void mapToTradeByItemIdManager_should_map_inputs_to_manager_with_valid_inputs() {
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_ITEM_ID, "itemId"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_SELL_PRICE, "160"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE, "120"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_BUY_PRICE, "120"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_BUY_PRICE, "360"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_PRIORITY, "2"));

        TradeByItemIdManager expected = new TradeByItemIdManager();
        expected.setTradeType(TradeManagerTradeType.BUY);
        expected.setItemId("itemId");
        expected.setSellStartingPrice(160);
        expected.setSellBoundaryPrice(120);
        expected.setBuyStartingPrice(120);
        expected.setBuyBoundaryPrice(360);
        expected.setPriority(2);

        Item item = new Item();
        item.setMinSellPrice(0);
        item.setMaxBuyPrice(120);
        item.setLimitMinPrice(120);
        item.setLimitMaxPrice(150_000);

        assertEquals(expected, tradeManagerFromInputsMapper.mapToTradeByItemIdManager(inputs, TradeManagerTradeType.BUY, item));
    }

    @Test
    public void mapToTradeByItemIdManager_should_map_inputs_to_manager_with_invalid_inputs() {
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_ITEM_ID, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_SELL_PRICE, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_BUY_PRICE, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_BUY_PRICE, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_PRIORITY, ""));

        TradeByItemIdManager expected = new TradeByItemIdManager();
        expected.setTradeType(TradeManagerTradeType.SELL);
        expected.setItemId("");
        expected.setSellBoundaryPrice(149);
        expected.setSellStartingPrice(149);
        expected.setBuyBoundaryPrice(120);
        expected.setBuyStartingPrice(120);
        expected.setPriority(1);

        Item item = new Item();
        item.setMinSellPrice(150);
        item.setMaxBuyPrice(0);
        item.setLimitMinPrice(120);
        item.setLimitMaxPrice(150_000);

        when(profitAndPriorityCalculator.calculateNextBuyPrice(item)).thenReturn(120);

        TradeByItemIdManager actual = tradeManagerFromInputsMapper.mapToTradeByItemIdManager(inputs, TradeManagerTradeType.SELL, item);

        assertEquals(expected, actual);
    }

    @Test
    public void mapToTradeByItemIdManager_should_map_inputs_to_manager_with_wrong_conjunctions_of_prices() {
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_ITEM_ID, "itemId"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_SELL_PRICE, "150"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE, "161"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_BUY_PRICE, "160"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_BUY_PRICE, "152"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_PRIORITY, "1"));

        TradeByItemIdManager expected = new TradeByItemIdManager();
        expected.setTradeType(TradeManagerTradeType.BUY_AND_SELL);
        expected.setItemId("itemId");
        expected.setSellBoundaryPrice(161);
        expected.setSellStartingPrice(161);
        expected.setBuyBoundaryPrice(152);
        expected.setBuyStartingPrice(152);
        expected.setPriority(1);

        Item item = new Item();
        item.setMinSellPrice(150);
        item.setMaxBuyPrice(0);
        item.setLimitMinPrice(120);
        item.setLimitMaxPrice(150_000);

        when(profitAndPriorityCalculator.calculateNextBuyPrice(item)).thenReturn(120);

        TradeByItemIdManager actual = tradeManagerFromInputsMapper.mapToTradeByItemIdManager(inputs, TradeManagerTradeType.BUY_AND_SELL, item);

        assertEquals(expected, actual);
    }

    @Test
    public void mapToTradeByItemIdManager_should_map_inputs_to_manager_with_out_of_limit_low_prices() {
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_ITEM_ID, "itemId"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_SELL_PRICE, "110"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE, "110"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_BUY_PRICE, "110"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_BUY_PRICE, "110"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_PRIORITY, "1"));

        TradeByItemIdManager expected = new TradeByItemIdManager();
        expected.setTradeType(TradeManagerTradeType.BUY_AND_SELL);
        expected.setItemId("itemId");
        expected.setSellBoundaryPrice(120);
        expected.setSellStartingPrice(120);
        expected.setBuyBoundaryPrice(120);
        expected.setBuyStartingPrice(120);
        expected.setPriority(1);

        Item item = new Item();
        item.setMinSellPrice(150);
        item.setMaxBuyPrice(0);
        item.setLimitMinPrice(120);
        item.setLimitMaxPrice(150_000);

        when(profitAndPriorityCalculator.calculateNextBuyPrice(item)).thenReturn(120);

        TradeByItemIdManager actual = tradeManagerFromInputsMapper.mapToTradeByItemIdManager(inputs, TradeManagerTradeType.BUY_AND_SELL, item);

        assertEquals(expected, actual);
    }

    @Test
    public void mapToTradeByItemIdManager_should_map_inputs_to_manager_with_out_of_limit_high_prices() {
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_ITEM_ID, "itemId"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_SELL_PRICE, "151000"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE, "151000"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_STARTING_BUY_PRICE, "151000"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_BUY_PRICE, "151000"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_PRIORITY, "1"));

        TradeByItemIdManager expected = new TradeByItemIdManager();
        expected.setTradeType(TradeManagerTradeType.BUY_AND_SELL);
        expected.setItemId("itemId");
        expected.setSellBoundaryPrice(150_000);
        expected.setSellStartingPrice(150_000);
        expected.setBuyBoundaryPrice(150_000);
        expected.setBuyStartingPrice(150_000);
        expected.setPriority(1);

        Item item = new Item();
        item.setMinSellPrice(150);
        item.setMaxBuyPrice(0);
        item.setLimitMinPrice(120);
        item.setLimitMaxPrice(150_000);

        when(profitAndPriorityCalculator.calculateNextBuyPrice(item)).thenReturn(120);

        TradeByItemIdManager actual = tradeManagerFromInputsMapper.mapToTradeByItemIdManager(inputs, TradeManagerTradeType.BUY_AND_SELL, item);

        assertEquals(expected, actual);
    }

    @Test
    public void mapToTradeByFiltersManager_should_map_inputs_to_manager_with_valid_inputs() {
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_NAME, "name"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_TRADE_TYPE, Callbacks.TRADE_BY_FILTERS_MANAGER_TYPE_BUY_EDIT));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_MIN_BUY_SELL_PROFIT, "100"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_MIN_PROFIT_PERCENT, "30"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_PRIORITY, "2"));

        TradeByFiltersManager expected = new TradeByFiltersManager();
        expected.setName("name");
        expected.setTradeType(TradeManagerTradeType.BUY);
        expected.setAppliedFilters(List.of(new ItemFilter()));
        expected.setMinBuySellProfit(100);
        expected.setMinProfitPercent(30);
        expected.setPriority(2);

        assertEquals(expected, tradeManagerFromInputsMapper.mapToTradeByFiltersManager(inputs, 150_000, List.of(new ItemFilter())));
    }

    @Test
    public void mapToTradeByFiltersManager_should_map_inputs_to_manager_with_invalid_inputs() {
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_NAME, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_TRADE_TYPE, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_FILTERS_NAMES, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_MIN_BUY_SELL_PROFIT, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_MIN_PROFIT_PERCENT, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_PRIORITY, ""));

        TradeByFiltersManager expected = new TradeByFiltersManager();
        expected.setName("");
        expected.setTradeType(TradeManagerTradeType.BUY_AND_SELL);
        expected.setAppliedFilters(List.of());
        expected.setMinBuySellProfit(50);
        expected.setMinProfitPercent(20);
        expected.setPriority(1);

        assertEquals(expected, tradeManagerFromInputsMapper.mapToTradeByFiltersManager(inputs, 150_000, List.of()));
    }

    @Test
    public void mapToTradeByFiltersManager_should_map_inputs_to_manager_with_minimum_values() {
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_NAME, null));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_TRADE_TYPE, Callbacks.TRADE_BY_FILTERS_MANAGER_TYPE_SELL_EDIT));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_FILTERS_NAMES, null));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_MIN_BUY_SELL_PROFIT, "-150001"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_MIN_PROFIT_PERCENT, "-2147483647"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_PRIORITY, "-2147483647"));

        TradeByFiltersManager expected = new TradeByFiltersManager();
        expected.setName(null);
        expected.setTradeType(TradeManagerTradeType.SELL);
        expected.setAppliedFilters(List.of());
        expected.setMinBuySellProfit(-150_000);
        expected.setMinProfitPercent(-2147483647);
        expected.setPriority(1);

        assertEquals(expected, tradeManagerFromInputsMapper.mapToTradeByFiltersManager(inputs, 150_000, List.of()));
    }

    @Test
    public void mapToTradeByFiltersManager_should_map_inputs_to_manager_with_maximum_values() {
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_NAME, "name"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_TRADE_TYPE,
                Callbacks.TRADE_BY_FILTERS_MANAGER_TYPE_BUY_AND_SELL_EDIT));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_FILTERS_NAMES, null));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_MIN_BUY_SELL_PROFIT, "150001"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_MIN_PROFIT_PERCENT, "2147483647"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADE_BY_FILTERS_MANAGER_EDIT_PRIORITY, "2147483647"));

        TradeByFiltersManager expected = new TradeByFiltersManager();
        expected.setName("name");
        expected.setTradeType(TradeManagerTradeType.BUY_AND_SELL);
        expected.setAppliedFilters(List.of());
        expected.setMinBuySellProfit(150_000);
        expected.setMinProfitPercent(Integer.MAX_VALUE);
        expected.setPriority(Integer.MAX_VALUE);

        assertEquals(expected, tradeManagerFromInputsMapper.mapToTradeByFiltersManager(inputs, 150_000, List.of()));
    }
}