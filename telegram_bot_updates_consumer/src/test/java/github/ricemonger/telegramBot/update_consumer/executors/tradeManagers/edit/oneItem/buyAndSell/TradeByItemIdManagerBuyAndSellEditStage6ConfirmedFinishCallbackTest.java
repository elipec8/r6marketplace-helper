package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.edit.oneItem.buyAndSell;

import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeByItemIdManagerBuyAndSellEditStage6ConfirmedFinishCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_save_user_trade_manager_by_user_inputs_and_notify_user() {
        TradeByItemIdManagerBuyAndSellEditStage6ConfirmedFinishCallback commandExecutor = new TradeByItemIdManagerBuyAndSellEditStage6ConfirmedFinishCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserTradeByItemIdManagerByUserInput(MockUpdateInfos.UPDATE_INFO.getChatId(), TradeOperationType.BUY_AND_SELL);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}