package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.sell;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeByItemIdManagerSellEditStage5ConfirmedFinishCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_save_user_trade_manager_and_notify_user() {
        TradeByItemIdManagerSellEditStage5ConfirmedFinishCallback commandExecutor = new TradeByItemIdManagerSellEditStage5ConfirmedFinishCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserTradeByItemIdManagerByUserInput(MockUpdateInfos.UPDATE_INFO.getChatId(), TradeOperationType.SELL);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}