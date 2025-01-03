package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buyAndSell;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buy.TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInput;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TradeByItemIdManagerBuyAndSellEditStage2AskBoundarySellPriceInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_middle_input_and_print_chosen_item_if_item_is_found() {
        when(botInnerService.getItemByUserInputItemId(any())).thenReturn(new Item());

        TradeByItemIdManagerBuyAndSellEditStage2AskBoundarySellPriceInput commandExecutor = new TradeByItemIdManagerBuyAndSellEditStage2AskBoundarySellPriceInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE);
        verify(botInnerService).getItemByUserInputItemId(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService,times(2)).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }

    @Test
    public void initAndExecute_should_not_process_middle_input_if_item_is_not_found() {
        when(botInnerService.getItemByUserInputItemId(any())).thenThrow(new ItemDoesntExistException(""));

        TradeByItemIdManagerBuyAndSellEditStage2AskBoundarySellPriceInput commandExecutor = new TradeByItemIdManagerBuyAndSellEditStage2AskBoundarySellPriceInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService,times(0)).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService,times(0)).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_BY_ITEM_ID_MANAGER_EDIT_BOUNDARY_SELL_PRICE);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}