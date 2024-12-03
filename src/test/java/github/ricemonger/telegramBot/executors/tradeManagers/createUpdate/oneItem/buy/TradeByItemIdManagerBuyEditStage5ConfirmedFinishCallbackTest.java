package github.ricemonger.telegramBot.executors.tradeManagers.createUpdate.oneItem.buy;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.executors.tradeManagers.edit.oneItem.buy.TradeByItemIdManagerBuyEditStage5ConfirmedFinishCallback;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeByItemIdManagerBuyEditStage5ConfirmedFinishCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_save_trade_manager_and_notify_user() {
        TradeByItemIdManagerBuyEditStage5ConfirmedFinishCallback commandExecutor = new TradeByItemIdManagerBuyEditStage5ConfirmedFinishCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserTradeByItemIdManagerByUserInput(MockUpdateInfos.UPDATE_INFO.getChatId(), TradeOperationType.BUY);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}