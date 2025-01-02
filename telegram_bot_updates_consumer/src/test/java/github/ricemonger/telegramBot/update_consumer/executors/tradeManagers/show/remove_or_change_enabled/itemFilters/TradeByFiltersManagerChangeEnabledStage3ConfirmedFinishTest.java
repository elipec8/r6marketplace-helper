package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.show.remove_or_change_enabled.itemFilters;

import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeByFiltersManagerChangeEnabledStage3ConfirmedFinishTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_invertUserTradeByFiltersManagerEnabledByUserInput_and_notify_user() {
        TradeByFiltersManagerChangeEnabledStage3ConfirmedFinishCallback commandExecutor = new TradeByFiltersManagerChangeEnabledStage3ConfirmedFinishCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).invertUserTradeByFiltersManagerEnabledByUserInput(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}