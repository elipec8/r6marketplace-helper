package github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemId;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.showRemoveChangeEnabled.remove_or_change_enabled.itemId.TradeByItemIdManagerRemoveStage3ConfirmedFinishCallback;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeByItemIdManagerRemoveStage3ConfirmedFinishCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_remove_trade_manager_by_user_input_and_notify_user() {
        TradeByItemIdManagerRemoveStage3ConfirmedFinishCallback commandExecutor = new TradeByItemIdManagerRemoveStage3ConfirmedFinishCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).removeUserTradeByItemIdManagerByUserInput(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}