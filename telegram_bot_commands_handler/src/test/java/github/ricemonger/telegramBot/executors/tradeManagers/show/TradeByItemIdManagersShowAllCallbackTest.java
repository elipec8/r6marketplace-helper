package github.ricemonger.telegramBot.executors.tradeManagers.show;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.show.TradeByItemIdManagersShowAllCallback;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeByItemIdManagersShowAllCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_ask_from_keyboard_and_send_user_all_item_id_trade_managers() {
        List<TradeByItemIdManager> tradeManagers = new ArrayList<>();
        when(botInnerService.getAllUserTradeByItemIdManagers(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(tradeManagers);

        TradeByItemIdManagersShowAllCallback commandExecutor = new TradeByItemIdManagersShowAllCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());

        verify(botInnerService).getAllUserTradeByItemIdManagers(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).sendMultipleObjectStringsGroupedInMessages(same(tradeManagers), anyInt(), eq(MockUpdateInfos.UPDATE_INFO.getChatId()));
    }
}