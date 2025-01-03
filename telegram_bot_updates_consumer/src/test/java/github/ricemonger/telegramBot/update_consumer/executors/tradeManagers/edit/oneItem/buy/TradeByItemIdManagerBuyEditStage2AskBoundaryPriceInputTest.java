package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buy;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_middle_input_and_print_chosen_item_if_item_is_found() {
        when(botInnerService.getItemByUserInputItemId(any())).thenReturn(new Item());

        TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInput commandExecutor = new TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);
        verify(botInnerService).getItemByUserInputItemId(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService,times(2)).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }

    @Test
    public void initAndExecute_should_not_process_middle_input_if_item_is_not_found() {
        when(botInnerService.getItemByUserInputItemId(any())).thenThrow(new ItemDoesntExistException(""));

        TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInput commandExecutor = new TradeByItemIdManagerBuyEditStage2AskBoundaryPriceInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService,times(0)).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService,times(0)).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_BY_ITEM_ID_MANAGER_BOUNDARY_BUY_PRICE);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}