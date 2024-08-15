package github.ricemonger.telegramBot.client;

import github.ricemonger.marketplace.services.ProfitAndPriorityCalculator;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.dtos.TradeByItemIdManager;
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
    public void mapToTradeManagerByItemId_should_map_inputs_to_manager_with_valid_inputs(){
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID, "itemId"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_STARTING_SELL_PRICE, "160"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_SELL_PRICE, "120"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_STARTING_BUY_PRICE, "120"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_BUY_PRICE, "360"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_PRIORITY, "2"));

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

        assertEquals(expected, tradeManagerFromInputsMapper.mapToTradeManagerByItemId(inputs, TradeManagerTradeType.BUY, item));
    }

    @Test
    public void mapToTradeManagerByItemId_should_map_inputs_to_manager_with_invalid_inputs(){
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_STARTING_SELL_PRICE, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_SELL_PRICE, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_STARTING_BUY_PRICE, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_BUY_PRICE, ""));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_PRIORITY, ""));

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

        TradeByItemIdManager actual = tradeManagerFromInputsMapper.mapToTradeManagerByItemId(inputs, TradeManagerTradeType.SELL, item);

        assertEquals(expected,actual);
    }

    @Test
    public void mapToTradeManagerByItemId_should_map_inputs_to_manager_with_wrong_conjunctions_of_prices(){
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID, "itemId"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_STARTING_SELL_PRICE, "150"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_SELL_PRICE, "161"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_STARTING_BUY_PRICE, "160"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_BUY_PRICE, "152"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_PRIORITY, "1"));

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

        TradeByItemIdManager actual = tradeManagerFromInputsMapper.mapToTradeManagerByItemId(inputs, TradeManagerTradeType.BUY_AND_SELL, item);

        assertEquals(expected,actual);
    }

    @Test
    public void mapToTradeManagerByItemId_should_map_inputs_to_manager_with_out_of_limit_low_prices(){
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID, "itemId"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_STARTING_SELL_PRICE, "110"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_SELL_PRICE, "110"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_STARTING_BUY_PRICE, "110"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_BUY_PRICE, "110"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_PRIORITY, "1"));

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

        TradeByItemIdManager actual = tradeManagerFromInputsMapper.mapToTradeManagerByItemId(inputs, TradeManagerTradeType.BUY_AND_SELL, item);

        assertEquals(expected,actual);
    }

    @Test
    public void mapToTradeManagerByItemId_should_map_inputs_to_manager_with_out_of_limit_high_prices(){
        String chatId = "chatId";
        List<TelegramUserInput> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID, "itemId"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_STARTING_SELL_PRICE, "151000"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_SELL_PRICE, "151000"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_STARTING_BUY_PRICE, "151000"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_BOUNDARY_BUY_PRICE, "151000"));
        inputs.add(new TelegramUserInput(chatId, InputState.TRADES_EDIT_ONE_ITEM_PRIORITY, "1"));

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

        TradeByItemIdManager actual = tradeManagerFromInputsMapper.mapToTradeManagerByItemId(inputs, TradeManagerTradeType.BUY_AND_SELL, item);

        assertEquals(expected,actual);
    }
}