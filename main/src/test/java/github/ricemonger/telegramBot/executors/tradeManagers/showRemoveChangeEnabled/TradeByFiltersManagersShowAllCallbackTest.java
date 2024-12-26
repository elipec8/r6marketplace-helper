package github.ricemonger.telegramBot.executors.tradeManagers.showRemoveChangeEnabled;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.utils.DTOs.personal.TradeByFiltersManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeByFiltersManagersShowAllCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_show_all_user_trade_by_filters_managers_and_ask_if_remove() {
        List<TradeByFiltersManager> tradeManagers = new ArrayList<>();
        tradeManagers.add(new TradeByFiltersManager());

        when(botInnerService.getAllUserTradeByFiltersManagers(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(tradeManagers);

        TradeByFiltersManagersShowAllCallback commandExecutor = new TradeByFiltersManagersShowAllCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).getAllUserTradeByFiltersManagers(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).sendMultipleObjectStringsGroupedInMessages(eq(tradeManagers), anyInt(), eq(MockUpdateInfos.UPDATE_INFO.getChatId()));

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}