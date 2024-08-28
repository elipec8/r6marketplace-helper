package github.ricemonger.telegramBot.executors.tradeManagers.showRemove.remove_or_change_enabled.itemId;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeByItemIdManagerRemoveOrChangeEnabledStage1AskItemIdCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_first_input() {
        TradeByItemIdManagerRemoveOrChangeEnabledStage1AskItemIdCallback commandExecutor = new TradeByItemIdManagerRemoveOrChangeEnabledStage1AskItemIdCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).clearUserInputs(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_BY_ITEM_ID_MANAGER_ITEM_ID);
        verify(botInnerService).setUserInputGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputGroup.TRADE_BY_ITEM_ID_MANAGER_SHOW_OR_REMOVE);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}