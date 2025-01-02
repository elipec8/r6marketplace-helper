package github.ricemonger.telegramBot.update_consumer.executors.items.settings.itemsInMessage;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemsShowSettingsChangeItemsInMessageCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_ask_from_keyboard() {
        ItemsShowSettingsChangeItemsInMessageCallback commandExecutor = new ItemsShowSettingsChangeItemsInMessageCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}