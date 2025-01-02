package github.ricemonger.telegramBot.update_consumer.executors.items;

import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemsDirectTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_ask_from_inline_keyboard() {
        ItemsDirect itemsDirect = new ItemsDirect();
        itemsDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}