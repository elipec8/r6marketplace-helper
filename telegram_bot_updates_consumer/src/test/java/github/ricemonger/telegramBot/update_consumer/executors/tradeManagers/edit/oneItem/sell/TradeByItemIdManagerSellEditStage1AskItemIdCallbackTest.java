package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.sell;

import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeByItemIdManagerSellEditStage1AskItemIdCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_first_input() {
        TradeByItemIdManagerSellEditStage1AskItemIdCallback commandExecutor = new TradeByItemIdManagerSellEditStage1AskItemIdCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID, InputGroup.TRADE_BY_ITEM_ID_MANAGER_TYPE_BUY_AND_SELL_EDIT);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}